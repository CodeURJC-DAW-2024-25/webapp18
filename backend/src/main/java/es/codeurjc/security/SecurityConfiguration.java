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
                http.authorizeHttpRequests(authorize -> authorize
                                // public pages
                                .requestMatchers(
                                                "/apartmentInformation/**",
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
                                                "/nickTaken",
                                                "/error",
                                                "/apartmentReviews/**",
                                                "/indexSearch",
                                                "/notRooms/**",
                                                "/notRooms**",
                                                "/captcha",
                                                "/loadMoreApartments/**",
                                                "/loadMoreReviews/**",
                                                "/profile/*/images/",
                                                "/static/images/**",
                                                "/index/*/images/**")
                                .permitAll()

                                // User pages
                                .requestMatchers(
                                                "/addReservation/**",
                                                "/profile/**",
                                                "/editProfile/**",
                                                "/editProfile/*/images",
                                                "/editProfileimage/**",
                                                "/postApartmentReviews/**",
                                                "/replace/**")
                                .hasAnyRole("USER")

                                // Client pages
                                .requestMatchers(
                                                "/addReservation/**",
                                                "/clientReservations",
                                                "/clientReservations/**",
                                                "/reservationInfo/**",
                                                "/addReservation/**",
                                                "/cancelReservation/**",
                                                "/postApartmentReviews/**",
                                                "/loadMoreReservations/**")
                                .hasAnyRole("CLIENT")

                                // Manager pages
                                .requestMatchers(
                                                "/editApartment/**",
                                                "/viewApartmentsManager",
                                                "/deleteApartment/**",
                                                "/chartsManager",
                                                "/addApartment",
                                                "/addApartment/**",
                                                "/testChart/**",
                                                "/application/**",
                                                "/index/*/images/**",
                                                "/editApartmentimage/**",
                                                "/editApartment",
                                                "/editApartment/**",
                                                "/updateApartment",
                                                "/updateApartment/**",
                                                "/selectApartmentImage/**",
                                                "/createApartment/**",
                                                "/createApartment",
                                                "/loadMoreApartmentsManagerView/**")
                                .hasAnyRole("MANAGER")

                                // Admin pages
                                .requestMatchers(
                                                "/managerValidation",
                                                "/acceptance/**",
                                                "/rejection/**")
                                .hasAnyRole("ADMIN")

                )
                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login")
                                                .failureUrl("/loginError")
                                                .defaultSuccessUrl("/profile")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login")
                                                .permitAll());

                return http.build();
        }

}