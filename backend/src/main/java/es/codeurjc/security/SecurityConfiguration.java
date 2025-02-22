/* package es.codeurjc.security;

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
*/
// SecurityConfiguration.java
package es.codeurjc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import es.codeurjc.service.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserSecurityService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/index", "/indexsearch", "/returnmainpage","/").permitAll()  // Index pública
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/vendor/**").permitAll()
                .requestMatchers("/login", "/loginerror", "/register", "/nickTaken", "/error").permitAll()
                .requestMatchers("/profile/**").hasAnyRole("USER", "CLIENT", "MANAGER", "ADMIN")
                .requestMatchers("/viewapartmentsmanager/**").hasRole("MANAGER")
                .requestMatchers("/chartsadmin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureUrl("/loginerror")
                .defaultSuccessUrl("/profile")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            );

        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
