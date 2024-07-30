package sn.edu.uadb.spring_security.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
 import sn.edu.uadb.DTO.AuthenticationDTO;
import sn.edu.uadb.DTO.InscriptionWithRole;
import sn.edu.uadb.spring_security.config.Jwt;
import sn.edu.uadb.spring_security.entite.User;
import sn.edu.uadb.spring_security.service.UserServiceInterface;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@AllArgsConstructor
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserServiceInterface userServiceInterface;
    private Jwt jwt;
    @PostMapping("/inscription")    
    public User inscription(@RequestBody InscriptionWithRole inscriptionWithRole){
        return userServiceInterface.createUser(inscriptionWithRole);
    }
    @PostMapping("/connexion")
    public Map<String,String> connexion(@RequestBody AuthenticationDTO authenticationDTO){
        Authentication authenticate =authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationDTO.username(), authenticationDTO.password()));
            if(authenticate.isAuthenticated()){
               return jwt.generate(authenticationDTO.username());
            }
        return null;
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return userServiceInterface.getAllUser();
    }
}
