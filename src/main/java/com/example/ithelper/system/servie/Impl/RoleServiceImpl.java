package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.system.dao.RoleRepository;
import com.example.ithelper.system.entity.Role;
import com.example.ithelper.system.servie.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }

    //@Override
    //public List<Role> getRolesByName(String name) {
    //    return roleRepository.findAllByRoleNameContains(name);
    //}


    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public Role getRoleById(long id) {
        return roleRepository.getOne(id);
    }
}
