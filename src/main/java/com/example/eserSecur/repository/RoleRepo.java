package com.example.eserSecur.repository;

import com.example.eserSecur.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends CrudRepository<Role,Long> {
}
