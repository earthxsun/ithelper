package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(String name);

    List findAllByRoleNameContains(String name);


}
