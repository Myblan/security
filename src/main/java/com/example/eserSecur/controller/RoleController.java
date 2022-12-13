package com.example.eserSecur.controller;

import com.example.eserSecur.domain.Role;
import com.example.eserSecur.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("")
    public List<Role> getAllRole(){
        return roleService.getRoles();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "id") Long id) throws Exception {
        return ResponseEntity.ok().body(roleService.getRole(id));
    }
    @PostMapping("")
    public ResponseEntity<Role> createRole(@RequestBody Role role) throws Exception {
        return ResponseEntity.ok().body(roleService.createRole(role));
    }
    @PutMapping("")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) throws Exception {
        return ResponseEntity.ok().body(roleService.updateRole(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRoleById(@PathVariable(value = "id") Long id) throws Exception {
        return ResponseEntity.ok().body(roleService.deleteRole(id));
    }


}
