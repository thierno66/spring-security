package sn.edu.uadb.spring_security.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sn.edu.uadb.spring_security.entite.Role;
import sn.edu.uadb.spring_security.entite.User;
import sn.edu.uadb.spring_security.repository.UserRepository;
@Service
@AllArgsConstructor
public class UtilisateurService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRolesToAuthoritie(user.getRoles()));
    }

    private Collection<GrantedAuthority>  mapRolesToAuthoritie(List<Role> roles){
        return roles.stream().map(r->new SimpleGrantedAuthority(r.getNomRole())).collect(Collectors.toList());
    }
}
