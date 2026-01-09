package com.airline.management.security;

import com.airline.management.entity.Utilisateur;
import com.airline.management.repository.UtilisateurRepository;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur u = utilisateurRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable: " + username));

        String role = u.getRole() == null ? "PASSAGER" : u.getRole();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());

        return new User(u.getEmail(), u.getMotDePasse(), Collections.singleton(authority));
    }
}
