package sn.edu.uadb.spring_security.service;

import java.util.List;

import sn.edu.uadb.DTO.InscriptionWithRole;
import sn.edu.uadb.spring_security.entite.User;

public interface UserServiceInterface {
    User createUser(InscriptionWithRole user);
    List<User> getAllUser();
    User getUserById(Long id);
    User updateUser(User user,Long id);
    void deleteUser(Long id);
    void addRoleToUser(String username,String rolename);

}
