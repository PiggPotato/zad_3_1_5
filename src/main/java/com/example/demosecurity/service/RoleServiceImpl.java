package com.example.demosecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demosecurity.model.Role;
import com.example.demosecurity.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role findRoleOfId(Long id) {
        return roleRepository.findById(id).get();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }

}
