package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.AccountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountDataRepository extends JpaRepository<AccountData,Long> {

    List<AccountData> findAllByName(String name);

    @Query(value = "select distinct name from AccountData")
    List<String> getAllName();
}
