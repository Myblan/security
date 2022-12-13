package com.example.eserSecur.service;

import com.example.eserSecur.domain.Role;
import com.example.eserSecur.repository.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }


    public List<Role> getRoles(){
        List<Role> roles=new ArrayList<>();
        for (Role role:roleRepo.findAll())
            roles.add(role);
        return roles;
    }

    public Role getRole(Long id) throws Exception {
        if (!roleRepo.existsById(id))
            throw new Exception("id role to get dont exist");
        return roleRepo.findById(id).get();
    }

    public Role updateRole(Role role) throws Exception{
        if (!roleRepo.existsById(role.getId()))
            throw new Exception("role to update dont exist");
        roleRepo.save(role);
        return role;
    }

    public Role createRole(Role role) throws Exception{
        roleRepo.save(role);
        return role;
    }

    public Role deleteRole(Long id) throws Exception{

        if (!roleRepo.existsById(id))
            throw new Exception("role to cancel dont exist");
        Role role=getRole(id);
        roleRepo.deleteById(id);
        return role;
    }


}
