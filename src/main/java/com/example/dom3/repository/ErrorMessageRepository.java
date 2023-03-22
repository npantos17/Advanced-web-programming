package com.example.dom3.repository;

import com.example.dom3.models.ErrorMessage;
import com.example.dom3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Collection;

public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Collection<ErrorMessage> findAllByMachine_CreatedBy (User user);
}
