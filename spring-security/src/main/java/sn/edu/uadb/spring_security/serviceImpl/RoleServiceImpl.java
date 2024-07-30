package sn.edu.uadb.spring_security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sn.edu.uadb.spring_security.entite.Role;
import sn.edu.uadb.spring_security.repository.RoleRepository;
import sn.edu.uadb.spring_security.service.RoleServiceInterface;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleServiceInterface {
    private RoleRepository roleRepository;

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        Optional<Role> optionalRole= roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public Role updateRole(Role role, Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if(optionalRole.isPresent()){
            Role existingRole=optionalRole.get();
            existingRole.setNomRole(role.getNomRole());
            return roleRepository.save(existingRole);
        }
        else throw new RuntimeException("Erreur lors de la modification du role");
    }

    @Override
    public void deleteRole(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isPresent()){
            roleRepository.delete(roleOptional.get());
        }
        else new RuntimeException("Erreur lors de la modification du role");
    }

}
