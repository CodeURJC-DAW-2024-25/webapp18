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
                .requestMatchers("/", "/register", "/register/**", "/login", "/logout", "/static/**", "/css/**", "/js/**", "/images/**").permitAll() // Permitir acceso público
                .requestMatchers("/profile").authenticated() // El perfil requiere autenticación
                .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
            )
            .formLogin(form -> form
                .loginPage("/login").permitAll()         // Página de inicio de sesión
                .loginProcessingUrl("/login")            // URL donde se procesa el formulario de login
                .usernameParameter("username")           // Nombre del parámetro para el email (por defecto: "username")
                .passwordParameter("password")           // Nombre del parámetro para la contraseña
                .defaultSuccessUrl("/profile", true)     // Redirige al perfil tras un login exitoso
                .failureUrl("/login?error=true")         // Redirige a /login si hay error
            )
            .logout(logout -> logout
                .logoutUrl("/logout")                    // URL para cerrar sesión
                .logoutSuccessUrl("/login?logout").permitAll() // Redirige tras cerrar sesión
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // Codificador de contraseñas
    }
}
