package com.example.prenotazionibe.service;

import com.example.prenotazionibe.model.entity.AppRole;
import com.example.prenotazionibe.model.entity.AppUser;
import com.example.prenotazionibe.repository.AppRoleRepository;
import com.example.prenotazionibe.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AppUser addNewUser(AppUser appUser) {
        String pw=appUser.getPassword();
        appUser.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
          AppUser appUser=appUserRepository.findByUsername(username);
          AppRole appRole=appRoleRepository.findByRoleName(roleName);
          appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        return appUserRepository.findAll();
    }
}
