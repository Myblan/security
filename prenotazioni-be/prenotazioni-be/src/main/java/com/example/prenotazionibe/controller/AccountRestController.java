package com.example.prenotazionibe.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.prenotazionibe.config.JWTUtil;
import com.example.prenotazionibe.model.entity.AppRole;
import com.example.prenotazionibe.model.entity.AppUser;
import com.example.prenotazionibe.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountRestController {

    private final AccountService accountService;

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers(){
        return accountService.listUsers();
    }
    @PostMapping("/users")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppUser saveUser(@RequestBody AppUser appUser){
         return accountService.addNewUser(appUser);
    }

    @PostMapping("/roles")
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole saveRole(@RequestBody AppRole appRole){
           return  accountService.addNewRole(appRole);
    }

    @PostMapping("/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm){
           accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }
    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
         String authToken=request.getHeader(JWTUtil.AUTH_HEADER);
         if (authToken!=null && authToken.startsWith(JWTUtil.PREFIX)){
             try {
                 String jwt=authToken.substring(JWTUtil.PREFIX.length());
                 Algorithm algorithm=Algorithm.HMAC256(JWTUtil.SECRET);
                 JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                 DecodedJWT decodedJWT=jwtVerifier.verify(jwt);
                 String username=decodedJWT.getSubject();
                 AppUser appUser=accountService.loadUserByUsername(username);
                 String jwtAccessToken= JWT.create()
                         .withSubject(appUser.getUsername())
                         .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACCESS_TOKEN))
                         .withIssuer(request.getRequestURL().toString())
                         .withClaim("roles",appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                         .sign(algorithm);
                 Map<String,String> idToken=new HashMap<>();
                 idToken.put("access-token",jwtAccessToken);
                 idToken.put("refresh-token",jwt);
                 response.setContentType("application/json");
                 new ObjectMapper().writeValue(response.getOutputStream(),idToken);
             }catch (Exception e){
                 throw e;
             }
         }
         else {
             throw new RuntimeException("refresh token required !!");
         }
    }
    @GetMapping("/profile")
    public AppUser profile(Principal principal){
          return accountService.loadUserByUsername(principal.getName());
    }
}

@Data
class RoleUserForm{
    private String username;
    private String roleName;
}
