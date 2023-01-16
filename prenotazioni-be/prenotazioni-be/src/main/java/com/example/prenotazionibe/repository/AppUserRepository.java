package com.example.prenotazionibe.repository;

import com.example.prenotazionibe.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByUsername(String username);
}
