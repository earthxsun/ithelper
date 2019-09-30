package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.Dept;
import com.example.ithelper.system.entity.Role;
import com.example.ithelper.system.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);

    //List<User> findAllByUsernameContaining(String username);

    List<User> findAllByUsernameContaining(String username, Pageable pageable);

    List<User> findAllByRole(Role role);

    List<User> findAllByRole(Role role, Pageable pageable);

    List<User> findAllByDept(Dept dept);

    List<User> findAllByDept(Dept dept, Pageable pageable);

    List<User> findAllByStatus(String status);

    List<User> findAllByStatus(String status,Pageable pageable);
}
