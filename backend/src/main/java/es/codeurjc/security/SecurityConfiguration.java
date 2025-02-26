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
                        .requestMatchers("/vendor/fontawesome-free/webfonts/**").permitAll() // Añadir esta línea
                        .requestMatchers("/favicon.ico").permitAll() // Añadir esta línea
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/index").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/nickTaken").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/notfounderror/**").permitAll()
                        .requestMatchers("/hotelReviews/**").permitAll()
                        .requestMatchers("/apartmentInformation/**").permitAll()
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
                        .requestMatchers("/posthotelReviews/**").hasAnyRole("USER")
                        .requestMatchers("/replace/**").hasAnyRole("USER")

                        // Client pages
                        .requestMatchers("/clientreservations/**").hasAnyRole("CLIENT")
                        .requestMatchers("/reservationInfo/**").hasAnyRole("CLIENT")
                        .requestMatchers("/addReservation/**").hasAnyRole("CLIENT")
                        .requestMatchers("/cancelReservation/**").hasAnyRole("CLIENT")
                        .requestMatchers("/posthotelReviews/**").hasAnyRole("CLIENT")
                        .requestMatchers("/loadMoreReservations/**").hasAnyRole("CLIENT")

                        // Manager pages
                        .requestMatchers("/edithotel/**").hasAnyRole("MANAGER")
                        .requestMatchers("/viewhotelsmanager").hasAnyRole("MANAGER")
                        .requestMatchers("/deleteHotel/**").hasAnyRole("MANAGER")
                        .requestMatchers("/clientlist/**").hasAnyRole("MANAGER")
                        .requestMatchers("/chartsmanager").hasAnyRole("MANAGER")
                        .requestMatchers("/addHotel/**").hasAnyRole("MANAGER")
                        .requestMatchers("/testChart/**").hasAnyRole("MANAGER")
                        .requestMatchers("/application/**").hasAnyRole("MANAGER")
                        .requestMatchers("/index/*/images/**").hasAnyRole("MANAGER")
                        .requestMatchers("/edithotelimage/**").hasAnyRole("MANAGER")
                        .requestMatchers("/addHotelPhoto/**").hasAnyRole("MANAGER")
                        .requestMatchers("/selecthotelimage/**").hasAnyRole("MANAGER")
                        .requestMatchers("/createHotel/**").hasAnyRole("MANAGER")
                        .requestMatchers("/chartsManager").hasAnyRole("MANAGER")
                        .requestMatchers("/addHotel").hasAnyRole("MANAGER")
                        .requestMatchers("/index/*/images/**").hasAnyRole("MANAGER")
                        .requestMatchers("/edithotelimage/**").hasAnyRole("MANAGER")
                        .requestMatchers("/loadMoreHotelsManagerView/**").hasAnyRole("MANAGER")
                        

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