package com.example.prenotazionibe.repository;

import com.example.prenotazionibe.model.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

    AppRole findByRoleName(String roleName);
}
