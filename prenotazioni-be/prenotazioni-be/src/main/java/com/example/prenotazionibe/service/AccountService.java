package com.example.prenotazionibe.service;

import com.example.prenotazionibe.model.entity.AppRole;
import com.example.prenotazionibe.model.entity.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username,String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
