package com.example.ithelper.system.servie;

import com.example.ithelper.system.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    //List<Role> getRolesByName(String name);

    Role getRoleByName(String name);

    Role getRoleById(long id);
}
