package com.example.dom3.repository;

import com.example.dom3.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    public Permission findByName(String name);


}
