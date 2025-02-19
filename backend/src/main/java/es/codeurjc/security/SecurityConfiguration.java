package es.codeurjc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/static/**", "/css/**", "/js/**", "/images/**").permitAll() // Permitir acceso a recursos estáticos
                .anyRequest().authenticated() // Otras rutas requieren autenticación
            )
            .formLogin(form -> form
                .loginPage("/login").permitAll()     // Página de inicio de sesión
                .loginProcessingUrl("/login")        // URL para procesar el formulario
                .defaultSuccessUrl("/profile", true) // Redirige al profile tras login
                .failureUrl("/login?error=true")     // Redirige si hay error
            )

            .logout(logout -> logout
                .logoutUrl("/logout") // URL para cerrar sesión
                .logoutSuccessUrl("/login?logout").permitAll() // Redirige tras cerrar sesión
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

