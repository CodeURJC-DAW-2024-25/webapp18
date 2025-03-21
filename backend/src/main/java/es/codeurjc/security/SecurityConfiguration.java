package es.codeurjc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
            .csrf(csrf -> csrf.disable())
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(authorize -> authorize
                // Endpoints públicos
                .requestMatchers(
                    "/api/v1/users/login",
                    "/api/v1/users/register",
                    "/api/v1/users/all",
                    "/api/v1/users/{id}",
                    "/api/v1/users/{id}/image"
                ).permitAll()

                // Endpoints que requieren autenticación
                .requestMatchers(
                    "/api/v1/users/profile",
                    "/api/v1/users/{id}/application",
                    "/api/v1/users/{id}",
                    "/api/v1/users/{id}/profile-image"
                ).authenticated()

                // Endpoints restringidos solo a ADMIN
                .requestMatchers(
                    "/api/v1/users/{id}/validate",
                    "/api/v1/users/{id}/reject"
                ).hasRole("ADMIN")

                // Rutas públicas adicionales
                .requestMatchers(
                    "/apartmentInformation/**",
                    "/apartments/**",
                    "/apartmentReviews/**",
                    "/login",
                    "/loginError",
                    "/css/**",
                    "/js/**",
                    "/fonts/**",
                    "/vendor/**",
                    "/favicon.ico",
                    "/images/**",
                    "/img/**",
                    "/",
                    "/index",
                    "/register",
                    "/nickTaken**",
                    "/error",
                    "/apartmentReviews/**",
                    "/indexSearch",
                    "/notRooms/**",
                    "/notRooms**",
                    "/captcha",
                    "/profile/*/images",
                    "/loadMoreApartments/**",
                    "/loadMoreReviews/**",
                    "/profile/*/images/",
                    "/static/images/**",
                    "/index/*/images/**"
                ).permitAll()

                // Páginas accesibles para usuarios autenticados
                .requestMatchers(
                    "/addReservation/**",
                    "/profile/**",
                    "/editProfile/**",
                    "/editProfile/*/images",
                    "/editProfileImage/**",
                    "/postApartmentReviews/**",
                    "/replace/**"
                ).hasAnyRole("USER")

                // Páginas accesibles para clientes
                .requestMatchers(
                    "/addReservation/**",
                    "/clientReservations",
                    "/clientReservations/**",
                    "/reservationInfo/**",
                    "/cancelReservation/**",
                    "/postApartmentReviews/**",
                    "/loadMoreReservations/**"
                ).hasAnyRole("CLIENT")

                // Páginas accesibles para managers
                .requestMatchers(
                    "/editApartment/**",
                    "/viewApartmentsManager",
                    "/deleteApartment/**",
                    "/chartsManager",
                    "/addApartment",
                    "/testChart/**",
                    "/application/**",
                    "/editApartmentImage/**",
                    "/editApartment",
                    "/updateApartment",
                    "/selectApartmentImage/**",
                    "/createApartment/**",
                    "/loadMoreApartmentsManagerView/**"
                ).hasAnyRole("MANAGER")

                // Páginas accesibles solo para administradores
                .requestMatchers(
                    "/managerValidation",
                    "/acceptance/**",
                    "/rejection/**"
                ).hasAnyRole("ADMIN")
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureUrl("/loginError")
                .defaultSuccessUrl("/profile", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Asegura que la sesión se cree si es necesaria
            );

        return http.build();
    }
}
