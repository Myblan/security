package com.example.eserSecur.controller;

import com.example.eserSecur.domain.User;
import com.example.eserSecur.service.RoleService;
import com.example.eserSecur.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("")
    public List<User> getAllUsers(){
        return userService.getUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id) throws Exception {
        return ResponseEntity.ok().body(userService.getUser(id));
    }
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok().body(userService.createUser(user));
    }
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable(value = "id") Long id) throws Exception {
        return ResponseEntity.ok().body(userService.deleteUser(id));
    }
    @PostMapping("/addroletoUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm roleToUserForm) throws Exception {

        userService.addRoleToUser(roleToUserForm.username,roleToUserForm.roleName);
        return ResponseEntity.ok().build();
    }

}

@Data
class RoleToUserForm{
    String username;
    String roleName;
}
