package sn.edu.uadb.spring_security.service;

import java.util.List;

import sn.edu.uadb.spring_security.entite.Role;

public interface RoleServiceInterface {
    Role createRole(Role role);
    List<Role> getAllRole();
    Role getRoleById(Long id);
    Role updateRole(Role role,Long id);
    void deleteRole(Long id);

}
