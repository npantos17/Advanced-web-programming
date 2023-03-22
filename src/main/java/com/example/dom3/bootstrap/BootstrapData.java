package com.example.dom3.bootstrap;

import com.example.dom3.models.Permission;
import com.example.dom3.models.User;
import com.example.dom3.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserService userService;


    @Autowired
    public BootstrapData(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading data");

        userService.savePermission(new Permission(null, "can_read_users"));
        userService.savePermission(new Permission(null, "can_create_users"));
        userService.savePermission(new Permission(null, "can_update_users"));
        userService.savePermission(new Permission(null, "can_delete_users"));
        userService.savePermission(new Permission(null, "can_search_machines"));
        userService.savePermission(new Permission(null, "can_start_machines"));
        userService.savePermission(new Permission(null, "can_stop_machines"));
        userService.savePermission(new Permission(null, "can_restart_machines"));
        userService.savePermission(new Permission(null, "can_create_machines"));
        userService.savePermission(new Permission(null, "can_destroy_machines"));
        userService.savePermission(new Permission(null, "can_schedule_machines"));



        userService.newUser(new User(null, "Nikola", "Pantos", "npantos@raf.rs", "123"));
        userService.newUser(new User(null, "Pera", "Peric", "pperic@raf.rs", "123"));

        userService.addPermissionToUser("npantos@raf.rs", "can_read_users");
        userService.addPermissionToUser("npantos@raf.rs", "can_create_users");
        userService.addPermissionToUser("npantos@raf.rs", "can_update_users");
        userService.addPermissionToUser("npantos@raf.rs", "can_delete_users");

        userService.addPermissionToUser("npantos@raf.rs", "can_search_machines");
        userService.addPermissionToUser("npantos@raf.rs", "can_start_machines");
        userService.addPermissionToUser("npantos@raf.rs", "can_stop_machines");
        userService.addPermissionToUser("npantos@raf.rs", "can_restart_machines");
        userService.addPermissionToUser("npantos@raf.rs", "can_create_machines");
        userService.addPermissionToUser("npantos@raf.rs", "can_destroy_machines");
        userService.addPermissionToUser("npantos@raf.rs", "can_schedule_machines");

        userService.addPermissionToUser("pperic@raf.rs", "can_read_users");
        userService.addPermissionToUser("pperic@raf.rs", "can_update_users");
        userService.addPermissionToUser("pperic@raf.rs", "can_start_machines");

//        machineService.createMachine("Machine #1","npantos@raf.rs");
//        machineService.createMachine("Machine #2","pperic@raf.rs");


        System.out.println("Data loaded");

    }
}
