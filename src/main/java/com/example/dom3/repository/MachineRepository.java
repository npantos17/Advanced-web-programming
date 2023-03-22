package com.example.dom3.repository;

import com.example.dom3.models.Machine;
import com.example.dom3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Collection<Machine> findAllByCreatedBy(User user);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    public Optional<Machine> findById(Long id);

}
