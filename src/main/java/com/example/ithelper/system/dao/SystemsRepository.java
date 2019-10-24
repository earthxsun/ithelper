package com.example.ithelper.system.dao;

import com.example.ithelper.system.entity.Systems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemsRepository extends JpaRepository<Systems,Long> {
    Systems findByName(String name);
}
