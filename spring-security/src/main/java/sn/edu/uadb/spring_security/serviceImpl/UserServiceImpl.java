package sn.edu.uadb.spring_security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sn.edu.uadb.spring_security.DTO.InscriptionWithRole;
import sn.edu.uadb.spring_security.entite.Role;
import sn.edu.uadb.spring_security.entite.User;
import sn.edu.uadb.spring_security.exception.UserNotFoundExeception;
import sn.edu.uadb.spring_security.repository.RoleRepository;
import sn.edu.uadb.spring_security.repository.UserRepository;
import sn.edu.uadb.spring_security.service.UserServiceInterface;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserServiceInterface {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
   BCryptPasswordEncoder passwordEncoder;
    @Override
    public User createUser(InscriptionWithRole inscriptionWithRole) {
        Role role =roleRepository.findByNomRole(inscriptionWithRole.getNomRole());
        User user= new User();
        String password = this.passwordEncoder.encode(inscriptionWithRole.getPassword());
        user.setPassword(password);
        user.setUsername(inscriptionWithRole.getUsername());
        user.getRoles().add(role);
        if(!user.getUsername().contains("@"))
            throw new RuntimeException("Vote Email invalide");
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user= userRepository.findById(id);
        return user.orElse(null);
    }


    @Override
    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
             userRepository.delete(userOptional.get());
        }
        else
          throw new UserNotFoundExeception("L'utilisateur dont l'id est "+id+" n'existe pas");
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        User user= userRepository.findByUsername(username);
        Role role = roleRepository.findByNomRole(rolename);
        user.getRoles().add(role);
        
    }

    @Override
    public User updateUser(User user, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User existingUser=optionalUser.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setRoles(user.getRoles());
            return userRepository.save(existingUser);
        }
        else 
        throw new UserNotFoundExeception("L'utilisateur ne peux pas etre modifier");
    }

}
