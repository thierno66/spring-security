package sn.edu.uadb.spring_security.config;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import sn.edu.uadb.spring_security.DTO.UserDto;

@Service
@AllArgsConstructor
public class Jwt {
    UtilisateurService utilisateurService;
    public Map<String,String> generate(String username){
        UserDetails userDetails = utilisateurService.loadUserByUsername(username);
        UserDto user = new UserDto(userDetails.getUsername(),userDetails.getPassword());
        return this.generateJwt(user);
    }
    private Map<String, String> generateJwt(UserDto user) {
        final Long creation =System.currentTimeMillis();
        final Long expiration =creation+30*60*1000;
        final Map<String,String>claims=Map.of(
            "username",user.getUsername(),
            "prenom","Thierno"
        );
       final String bearer= Jwts.builder()
            .setIssuedAt(new Date(creation))
            .setExpiration(new Date(expiration))
            .setSubject(user.getUsername())
            .setClaims(claims)
            .signWith(getKey(),SignatureAlgorithm.HS256)
            .compact()
        ;
        System.out.println("la creation du token"+user.getUsername());
        return Map.of("token",bearer);
    }
    private Key getKey() {
        String secretKey = "6LePO5jGQoHh5hPz5MPN3QBPZ5hBP7hGQePzQoHh3QEPz5hH7oZQePB7h3lH5M=="; 
        // Cette clé doit être sécurisée et gérée de manière appropriée
        
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Object extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
                System.out.println(claims.get("username"));
		return claims.get("username");
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token);
                return true;
        } catch (Exception e) {
            throw new RuntimeException("Le token n'ext pas valide" +e);
        }
    }
    
}   
     

