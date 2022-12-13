package com.example.eserSecur;

import com.example.eserSecur.domain.Role;
import com.example.eserSecur.domain.User;
import com.example.eserSecur.service.RoleService;
import com.example.eserSecur.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class EserSecurApplication {

	public static void main(String[] args) {
		SpringApplication.run(EserSecurApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService){

		return args -> {

			roleService.createRole(new Role("ROLE_USER"));
			roleService.createRole(new Role("ROLE_MANAGER"));
			roleService.createRole(new Role("ROLE_ADMIN"));
			roleService.createRole(new Role("ROLE_SUPER_ADMIN"));

			userService.createUser(new User("John Travolta","john","1234",new ArrayList<>()));
			userService.createUser(new User("Will Smith","will","1234",new ArrayList<>()));
			userService.createUser(new User("Jim Carry","jim","1234",new ArrayList<>()));
			userService.createUser(new User("Arnold Schwarzeneger","arnold","1234",new ArrayList<>()));

			userService.addRoleToUser("john","ROLE_USER");
			userService.addRoleToUser("john","ROLE_MANAGER");
			userService.addRoleToUser("will","ROLE_MANAGER");
			userService.addRoleToUser("jim","ROLE_ADMIN");
			userService.addRoleToUser("arnold","ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arnold","ROLE_ADMIN");
			userService.addRoleToUser("arnold","ROLE_USER");

		};
	}

}
