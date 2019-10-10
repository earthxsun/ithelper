package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.OperateLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperateLogRepository extends JpaRepository<OperateLog,Long> {
}
