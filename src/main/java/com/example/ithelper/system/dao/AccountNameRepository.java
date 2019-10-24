package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.AccountName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountNameRepository extends JpaRepository<AccountName,Long> {

    List<AccountName> findByAccountId(String accountId);

    List<AccountName> deleteAllByAccountIdContains(String accountId);
}
