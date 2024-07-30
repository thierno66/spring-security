package sn.edu.uadb.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.edu.uadb.spring_security.entite.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
