package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByNameContains(String name);

    List<Account> findAllByNameContains(String name, Pageable pageable);

    List<Account> findAllByApplicationType(String type);

    List<Account> findAllByApplicationType(String type, Pageable pageable);

    List<Account> findAllByDept(String dept);

    List<Account> findAllByDept(String dept, Pageable pageable);

    List<Account> findAllByStatus(String status);

    List<Account> findAllByStatus(String status, Pageable pageable);

    List<Account> findAllByCreateTimeBetween(Date date1,Date date2);

    List<Account> findAllByCreateTimeBetween(Date date1,Date date2,Pageable pageable);

    List<Account> findAllByDeptIn(List<String> list);

    List<Account> findAllByDeptIn(List<String> list,Pageable pageable);

    List<Account> findAllByDeptInAndNameContains(List<String> list,String name);

    List<Account> findAllByDeptInAndNameContains(List<String> list,String name,Pageable pageable);
}
