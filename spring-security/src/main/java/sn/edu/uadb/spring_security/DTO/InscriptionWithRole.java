package sn.edu.uadb.spring_security.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InscriptionWithRole {
    private String username;
    private String password;
    private String nomRole;
}
