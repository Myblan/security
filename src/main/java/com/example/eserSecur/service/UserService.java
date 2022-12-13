package com.example.eserSecur.service;

import com.example.eserSecur.domain.Role;
import com.example.eserSecur.domain.User;
import com.example.eserSecur.repository.RoleRepo;
import com.example.eserSecur.repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=null;
        for (User u:getUsers())
             if(username.equals(u.getUsername())){
                 user=u;
                 break;
             }
        if (user==null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    public List<User> getUsers(){
        List<User> users=new ArrayList<>();
        for (User user:userRepo.findAll())
            users.add(user);
        return users;
    }

    public User getUser(Long id) throws Exception {
        if (!userRepo.existsById(id))
            throw new Exception("id user to get dont exist");
        return userRepo.findById(id).get();
    }

    public User updateUser(User user) throws Exception{
        if (!userRepo.existsById(user.getId()))
            throw new Exception("user to update dont exist");
        userRepo.save(user);
        return user;
    }

    public User createUser(User user) throws Exception{

        for (Role role:user.getRoles()){
            if (!roleRepo.existsById(role.getId()))
                roleRepo.save(role);
        }
        userRepo.save(user);
        return user;
    }

    public User deleteUser(Long id) throws Exception{

        if (!userRepo.existsById(id))
            throw new Exception("user to cancel dont exist");
        User user=getUser(id);
        userRepo.deleteById(id);
        return user;
    }

    public User addRoleToUser(String username,String roleName) throws Exception{
        Role role=null;
        User user=null;

        for (Role r:roleRepo.findAll())
            if (r.getName().equals(roleName)){
                role=r;
                break;
            }

        for (User u:getUsers())
            if (u.getUsername().equals(username)){
                user=u;
                break;
            }


        user.getRoles().add(role);

        return updateUser(user);
    }

}
