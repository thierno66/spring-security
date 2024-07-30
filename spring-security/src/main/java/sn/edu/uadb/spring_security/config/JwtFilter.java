package sn.edu.uadb.spring_security.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private UtilisateurService utilisateurService;
    private Jwt jwt;
    public JwtFilter(UtilisateurService utilisateurService,Jwt jwt) {
        this.utilisateurService = utilisateurService;
        this.jwt=jwt;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        String token=null;
        String username=null;
        boolean isTokenExpire=false;

        String authorization = request.getHeader("Authorization");
        if(authorization !=null && authorization.startsWith("Bearer")){
            token=authorization.substring(7);
            System.out.println(token);
            isTokenExpire=jwt.isTokenValid(token);
            username=(String) jwt.extractUsername(token);
        }
        if (isTokenExpire && username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails utilisateur = utilisateurService.loadUserByUsername(username);
            System.out.println("dans dofilter"+utilisateur);
            UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(utilisateur,null ,utilisateur.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("authenticatin"+authenticationToken);
        }

        filterChain.doFilter(request, response);
        
    }

}
