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
                        .requestMatchers(
                                "/apartmentInformation/**",
                                "/apartmentReviews/**",
                                "/login",
                                "/loginerror",
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
                                "/notfounderror/**",
                                "/hotelReviews/**",
                                "/indexsearch",
                                "/notRooms/**",
                                "/notRooms**",
                                "/indexsearch",
                                "/returnmainpage",
                                "/captcha",
                                "/loadMoreApartments/**",
                                "/loadMoreReviews/**",
                                "/profile/*/images/",
                                "/static/images/**",
                                "/index/*/images/**")
                        .permitAll()

                        // User pages
                        .requestMatchers("/addReservation/**").hasAnyRole("USER")
                        .requestMatchers("/profile/**").hasAnyRole("USER")
                        .requestMatchers("/editprofile/**").hasAnyRole("USER")
                        .requestMatchers("/editprofile/*/images").hasAnyRole("USER")
                        .requestMatchers("/editprofileimage/**").hasAnyRole("USER")
                        .requestMatchers("/postapartmentReviews/**").hasAnyRole("USER")
                        .requestMatchers("/replace/**").hasAnyRole("USER")

                        // Client pages
                        .requestMatchers("/addReservation/**").hasAnyRole("CLIENT")
                        .requestMatchers("/clientReservations").hasAnyRole("CLIENT")
                        .requestMatchers("/clientReservations/**").hasAnyRole("CLIENT")
                        .requestMatchers("/reservationInfo/**").hasAnyRole("CLIENT")
                        .requestMatchers("/addReservation/**").hasAnyRole("CLIENT")
                        .requestMatchers("/cancelReservation/**").hasAnyRole("CLIENT")
                        .requestMatchers("/postapartmentReviews/**").hasAnyRole("CLIENT")
                        .requestMatchers("/loadMoreReservations/**").hasAnyRole("CLIENT")

                        // Manager pages
                        .requestMatchers(
                                "/editApartment/**",
                                "/viewApartmentsManager",
                                "/deleteApartment/**",
                                "/clientlist/**",
                                "/chartsmanager",
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
                                "/addApartmentPhoto/**",
                                "/selectApartmentimage/**",
                                "/createApartment/**",
                                "/createApartment",
                                "/chartsManager",
                                "/loadMoreApartmentsManagerView/**")
                        .hasAnyRole("MANAGER")

                        // Admin pages
                        .requestMatchers("/managerlist").hasAnyRole("ADMIN")
                        .requestMatchers("/chartsadmin").hasAnyRole("ADMIN")
                        .requestMatchers("/managerValidation").hasAnyRole("ADMIN")
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