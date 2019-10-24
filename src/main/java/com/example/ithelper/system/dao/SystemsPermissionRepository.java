package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.Systems;
import com.example.ithelper.system.entity.SystemsPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemsPermissionRepository extends JpaRepository<SystemsPermission,Long> {
    List<SystemsPermission> findAllBySystems(Systems systems);
}
