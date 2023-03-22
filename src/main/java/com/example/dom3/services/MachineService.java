package com.example.dom3.services;

import com.example.dom3.models.ErrorMessage;
import com.example.dom3.models.Machine;
import com.example.dom3.models.Status;
import com.example.dom3.repository.ErrorMessageRepository;
import com.example.dom3.repository.MachineRepository;
import com.example.dom3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class MachineService {

    private MachineRepository machineRepository;
    private UserRepository userRepository;
    private ErrorMessageRepository errorMessageRepository;
    private TaskScheduler taskScheduler;

    @Autowired
    public MachineService(MachineRepository machineRepository, UserRepository userRepository, TaskScheduler taskScheduler, ErrorMessageRepository errorMessageRepository) {
        this.machineRepository = machineRepository;
        this.userRepository = userRepository;
        this.taskScheduler = taskScheduler;
        this.errorMessageRepository = errorMessageRepository;

    }

    public Machine createMachine(String name, String userMail) {
        System.err.println("Masina se kreira");
        return machineRepository.save(new Machine(0L, Status.STOPPED, userRepository.findByEmail(userMail), true,name, LocalDate.now()));
    }

    @Transactional
    public void destroyMachine(Long id) {
        Optional<Machine> optionalMachine = this.findById(id);
        if (optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            if (machine.getStatus() != Status.STOPPED) return;
            machine.setActive(false);
            machineRepository.save(machine);
        }
    }
    @Transactional
    public Optional<Machine> findById(Long id) {
        return machineRepository.findById(id);
    }
    @Transactional
    @Async
    public void startMachine(Long id, boolean scheduled) throws InterruptedException {
        Optional<Machine> optionalMachine = machineRepository.findById(id);
        if(optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            if(machine.isActive()) {
                if (machine.getStatus() == Status.STOPPED) {
                    Thread.sleep((long) (Math.random() * (15000 - 10000) + 10000));
                    machine.setStatus(Status.RUNNING);
                    machineRepository.save(machine);
                } else
                if(scheduled)
                    errorMessageRepository.save(new ErrorMessage(0L, "Masina nije stopirana.", "START", LocalDate.now(), machine));
            } else
            if(scheduled)
                errorMessageRepository.save(new ErrorMessage(0L, "Masina deaktivirana.", "START",LocalDate.now(), machine));
        }
    }
    @Transactional
    public Collection<Machine> searchMachines(String name, List<String> statuses, LocalDate dateFrom, LocalDate dateTo, String userMail) {
        ArrayList<Machine> allMachinesByUser = (ArrayList<Machine>) getMachinesByUser(userMail);
        ArrayList<Machine> filteredMachines = new ArrayList<>();
        int addFlag;

        for (Machine machine : allMachinesByUser) {
            addFlag = 0;

            if (name != null && machine.getName().toLowerCase().contains(name.toLowerCase())) addFlag++;
            else if (name == null) addFlag++;

            if (statuses != null && statuses.contains(machine.getStatus().toString())) addFlag++;
            else if (statuses == null) addFlag++;

            if (dateFrom != null && dateTo != null && machine.getCreationDate().isAfter(dateFrom) && machine.getCreationDate().isBefore(dateTo))
                addFlag++;
            else if (dateFrom == null || dateTo == null) addFlag++;

            if (addFlag == 3) filteredMachines.add(machine);
        }
        return filteredMachines;
//        return null;
    }
    @Transactional
    public Collection<Machine> getMachinesByUser(String userMail) {
        return machineRepository.findAllByCreatedBy(userRepository.findByEmail(userMail));
    }

    @Async
    @Transactional
    public void stopMachine(Long id, boolean scheduled) throws InterruptedException {
        Optional<Machine> optionalMachine = machineRepository.findById(id);
        if(optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            if(machine.isActive()) {
                if (machine.getStatus() == Status.RUNNING) {
                    System.err.println("Zaustavljanjem masine...");
                    Thread.sleep((long) (Math.random() * (15000 - 10000) + 10000));
                    machine.setStatus(Status.STOPPED);
                    machineRepository.save(machine);
                    System.err.println("Zaustavljena");
                } else
                if(scheduled)
                    errorMessageRepository.save(new ErrorMessage(0L, "Status nije RUNNING.", "STOP", LocalDate.now(), machine));
            } else
            if(scheduled)
                errorMessageRepository.save(new ErrorMessage(0L, "Masina je deaktivirana..", "STOP",LocalDate.now(), machine));
        }
    }
    @Async
    @Transactional
    public void restartMachine(Long id, boolean scheduled) throws InterruptedException {
        Optional<Machine> optionalMachine = machineRepository.findById(id);
        if(optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            if(machine.isActive()) {
                if (machine.getStatus() == Status.RUNNING) {
                    System.err.println("Zaustavljanje.");
                    Thread.sleep((long) (Math.random() * (10000 - 5000) + 5000));
                    machine.setStatus(Status.STOPPED);
                    machineRepository.save(machine);

                    machine = this.findById(id).get();

                    System.err.println("Ponovno pokretanje");
                    Thread.sleep((long) (Math.random() * (10000 - 5000) + 5000));
                    machine.setStatus(Status.RUNNING);
                    machineRepository.save(machine);
                    System.err.println("Restartovana.");
                } else
                if(scheduled)
                    errorMessageRepository.save(new ErrorMessage(0L, "Status masine nije RUNNING.", "RESTART", LocalDate.now(), machine));
            } else
            if(scheduled)
                errorMessageRepository.save(new ErrorMessage(0L, "Masina je deaktivirana.", "RESTART",LocalDate.now(), machine));
        }
    }
    @Transactional
    public void scheduleMachine(Long id, String date, String time, String action) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date + " " + time);


        this.taskScheduler.schedule(() -> {
            try {
                switch (action) {
                    case "Start":
                        startMachine(id, true);
                        break;
                    case "Stop":
                        stopMachine(id, true);
                        break;
                    case "Restart":
                        restartMachine(id, true);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, date1);
    }
    @Transactional
    public Collection<ErrorMessage> findAllErrorsForUser(String userMail){
        return errorMessageRepository.findAllByMachine_CreatedBy(userRepository.findByEmail(userMail));
    }



}
