package sn.edu.uadb.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.edu.uadb.spring_security.entite.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByNomRole(String nomRole);
}
