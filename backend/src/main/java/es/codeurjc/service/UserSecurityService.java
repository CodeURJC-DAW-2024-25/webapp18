
package es.codeurjc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.codeurjc.model.UserE;
import es.codeurjc.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.debug("Intentando autenticar usuario con username: {}", username);

            // Búsqueda insensible a mayúsculas/minúsculas
            UserE user = userRepository.findByNick(username.toLowerCase())
                .orElseGet(() -> userRepository.findByEmail(username.toLowerCase())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username)));

            log.debug("Usuario encontrado: {}", user.getNick());

            if (user.getRols() == null || user.getRols().isEmpty()) {
                log.warn("Usuario {} no tiene roles asignados, asignando ROLE_USER por defecto", username);
                user.setRols(Arrays.asList("ROLE_USER"));
                userRepository.save(user);
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : user.getRols()) {
                String roleWithPrefix = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                authorities.add(new SimpleGrantedAuthority(roleWithPrefix));
                log.debug("Añadida autoridad: {}", roleWithPrefix);
            }

            log.debug("Autoridades finales: {}", authorities);

            return org.springframework.security.core.userdetails.User
                .withUsername(user.getNick())
                .password(user.getPass())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

        } catch (Exception e) {
            log.error("Error durante la autenticación del usuario: " + username, e);
            throw new UsernameNotFoundException("Error cargando usuario: " + username, e);
        }
    }
}
