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
        http.authenticationProvider(authenticationProvider());
        http
                .authorizeHttpRequests(authorize -> authorize
                        // public pages
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/loginerror").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/fonts/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/nickTaken").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/notfounderror/**").permitAll()
                        .requestMatchers("/apartmentReviews/**").permitAll()
                        .requestMatchers("/apartmentinformation/**").permitAll()
                        .requestMatchers("indexsearch").permitAll()
                        .requestMatchers("/notRooms/**").permitAll()
                        .requestMatchers("/indexsearch").permitAll()
                        .requestMatchers("/returnmainpage").permitAll()
                        .requestMatchers("/captcha").permitAll()
                        .requestMatchers("/loadMoreApartments/**").permitAll()
                        .requestMatchers("/loadMoreReviews/**").permitAll()
                        .requestMatchers("/profile/*/images/").permitAll()
                        .requestMatchers("/static/images/**").permitAll()
                        .requestMatchers("/index/*/images/**").permitAll()

                        // User pages
                        .requestMatchers("/profile/**").hasAnyRole("USER")
                        .requestMatchers("/editprofile/**").hasAnyRole("USER")
                        .requestMatchers("/editprofile/*/images").hasAnyRole("USER")
                        .requestMatchers("/editprofileimage/**").hasAnyRole("USER")
                        .requestMatchers("/postapartmentReviews/**").hasAnyRole("USER")
                        .requestMatchers("/replace/**").hasAnyRole("USER")

                        // Client pages
                        .requestMatchers("/clientreservations/**").hasAnyRole("CLIENT")
                        .requestMatchers("/reservationInfo/**").hasAnyRole("CLIENT")
                        .requestMatchers("/addReservation/**").hasAnyRole("CLIENT")
                        .requestMatchers("/cancelReservation/**").hasAnyRole("CLIENT")
                        .requestMatchers("/postapartmentReviews/**").hasAnyRole("CLIENT")
                        .requestMatchers("/loadMoreReservations/**").hasAnyRole("CLIENT")

                        // Manager pages
                        .requestMatchers("/editapartment/**").hasAnyRole("MANAGER")
                        .requestMatchers("/viewapartmentsmanager").hasAnyRole("MANAGER")
                        .requestMatchers("/deleteApartment/**").hasAnyRole("MANAGER")
                        .requestMatchers("/clientlist/**").hasAnyRole("MANAGER")
                        .requestMatchers("/chartsmanager").hasAnyRole("MANAGER")
                        .requestMatchers("/addApartment/**").hasAnyRole("MANAGER")
                        .requestMatchers("/testChart/**").hasAnyRole("MANAGER")
                        .requestMatchers("/application/**").hasAnyRole("MANAGER")
                        .requestMatchers("/index/*/images/**").hasAnyRole("MANAGER")
                        .requestMatchers("/editapartmentimage/**").hasAnyRole("MANAGER")
                        .requestMatchers("/addApartmentPhoto/**").hasAnyRole("MANAGER")
                        .requestMatchers("/selectapartmentimage/**").hasAnyRole("MANAGER")
                        .requestMatchers("/createApartment/**").hasAnyRole("MANAGER")
                        .requestMatchers("/chartsManager").hasAnyRole("MANAGER")
                        .requestMatchers("/addApartment").hasAnyRole("MANAGER")
                        .requestMatchers("/index/*/images/**").hasAnyRole("MANAGER")
                        .requestMatchers("/editapartmentimage/**").hasAnyRole("MANAGER")
                        .requestMatchers("/loadMoreApartmentsManagerView/**").hasAnyRole("MANAGER")

                        // Admin pages
                        .requestMatchers("/managerlist").hasAnyRole("ADMIN")
                        .requestMatchers("/chartsadmin").hasAnyRole("ADMIN")
                        .requestMatchers("/managervalidation").hasAnyRole("ADMIN")
                        .requestMatchers("/acceptance/**").hasAnyRole("ADMIN")
                        .requestMatchers("/rejection/**").hasAnyRole("ADMIN")

                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/loginerror")
                        .defaultSuccessUrl("/profile")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll());

        return http.build();
    }

}
