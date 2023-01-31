package com.example.demosecurity.service;

import com.example.demosecurity.model.Role;

import java.util.List;

public interface RoleService {

    public Role findRoleOfId(Long id);
    public List<Role> getAllRoles();
    public void addRole(Role role);


}
