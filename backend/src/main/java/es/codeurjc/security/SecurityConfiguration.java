package es.codeurjc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.codeurjc.security.jwt.JwtRequestFilter;
import es.codeurjc.security.jwt.UnauthorizedHandlerJwt;
import es.codeurjc.service.UserSecurityService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        @Autowired
        private UserSecurityService userDetailService;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Autowired
        private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

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
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

        // API security configuration (JWT-based, stateless, no CSRF)
        @Bean
        @Order(1)
        public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

                http.authenticationProvider(authenticationProvider());

                http
                                .securityMatcher("/api/**")
                                .exceptionHandling(
                                                handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

                http
                                .authorizeHttpRequests(auth -> auth
                                                /*  Public API endpoints
                                                .requestMatchers(
                                                                HttpMethod.POST,
                                                                "/api/v1/users/",
                                                                "/api/v1/users/login")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST,
                                                                "/api/v1/apartments/",
                                                                "/api/v1/apartments/{id}",
                                                                "/api/v1/apartments/{id}/info",
                                                                "/api/v1/apartments/{id}/images",
                                                                "/api/v1/apartments/loadMore",
                                                                "/api/v1/reviews/{id}",
                                                                "/api/v1/reviews/loadMore/**",
                                                                "/api/v1/rooms",
                                                                "/api/v1/rooms/{id}",
                                                                "/api/v1/rooms/filter",
                                                                "/api/v1/users/{id}/image",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()

                                                // Client: create and view reservations
                                                .requestMatchers(HttpMethod.POST,
                                                                "/api/v1/reservations")
                                                .hasRole("CLIENT")
                                                .requestMatchers(HttpMethod.GET,
                                                                "/api/v1/reservations/{id}","/api/v1/apartments/")
                                                .hasAnyRole("CLIENT", "ADMIN")

                                                // Manager: manage apartments
                                                .requestMatchers(HttpMethod.GET,
                                                                "/api/v1/apartments/manager/loadMore/**")
                                                .hasRole("MANAGER")
                                                .requestMatchers(HttpMethod.POST,
                                                                "/api/v1/apartments/",
                                                                "/api/v1/rooms")
                                                .hasRole("MANAGER")
                                                .requestMatchers(HttpMethod.PUT,
                                                                "/api/v1/apartments/{id}",
                                                                "/api/v1/managers/{id}/application",
                                                                "/api/v1/rooms/{id}")
                                                .hasRole("MANAGER")
                                                .requestMatchers(HttpMethod.DELETE,
                                                                "/api/v1/apartments/{id}",
                                                                "/api/v1/rooms/{id}")
                                                .hasRole("MANAGER")

                                                // Authenticated user routes
                                                .requestMatchers(HttpMethod.GET, "/api/v1/users/profile")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/image1")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/image2")
                                                .authenticated()

                                                // Everything else requires authentication
                                                .anyRequest().authenticated()); */


                                               
                                // Public API endpoints
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/users/",
                                        "/api/v1/users/login")
                                .permitAll()
                                
                                // Rutas GET públicas
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/apartments/{id}",
                                        "/api/v1/apartments/{id}/images",
                                        "/api/v1/apartments/{id}/info",
                                        "/api/v1/apartments/loadMore",
                                        "/api/v1/users/{id}/image",
                                        "/api/v1/reviews/{id}",
                                        "/api/v1/reviews/loadMore/{start}/{end}",
                                        "/api/v1/rooms",
                                        "/api/v1/rooms/{id}",
                                        "/api/v1/rooms/filter")
                                .permitAll()
                                
                                // Rutas POST permitidas
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/reviews/post/{id}")
                                .permitAll()
                                
                                // Client: create and view reservations
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/reservations")
                                .hasRole("CLIENT")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/reservations/{id}")
                                .hasAnyRole("CLIENT", "ADMIN")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v1/reservations/{id}")
                                .hasAnyRole("CLIENT", "ADMIN")
                                
                                // Apartments: lista principal accesible para usuarios autenticados
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/apartments/")
                                .hasAnyRole("MANAGER", "CLIENT", "ADMIN")
                                
                                // Manager: manage apartments
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/apartments/manager/loadMore/**")
                                .hasRole("MANAGER")
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/apartments/",
                                        "/api/v1/rooms")
                                .hasRole("MANAGER")
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        "/api/v1/apartments/{id}",
                                        "/api/v1/managers/{id}/application",
                                        "/api/v1/rooms/{id}")
                                .hasRole("MANAGER")
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        "/api/v1/apartments/{id}",
                                        "/api/v1/rooms/{id}")
                                .hasRole("MANAGER")
                                
                                // User management API
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/users/all")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/users/")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/users/{id}")
                                .authenticated()
                                
                                // Authenticated user routes
                                .requestMatchers(
                                        HttpMethod.GET, 
                                        "/api/v1/users/profile")
                                .authenticated()
                                .requestMatchers(
                                        HttpMethod.PUT, 
                                        "/api/v1/users/{id}")
                                .authenticated()
                                .requestMatchers(
                                        HttpMethod.PUT, 
                                        "/api/v1/users/{id}/application")
                                .authenticated()
                                .requestMatchers(
                                        HttpMethod.PUT, 
                                        "/api/v1/users/{id}/image1",
                                        "/api/v1/users/{id}/image2")
                                .authenticated()
                                
                                // Swagger/OpenAPI docs
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**")
                                .permitAll()
                                
                                // Everything else requires authentication
                                .anyRequest().authenticated());

                http.formLogin(form -> form.disable());
                http.csrf(csrf -> csrf.disable());
                http.httpBasic(httpBasic -> httpBasic.disable());
                http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        // Web (form-based) security configuration (sessions, CSRF enabled)
        @Bean
        @Order(2)
        public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
                http
                                .authenticationProvider(authenticationProvider())
                                .authorizeHttpRequests(authorize -> authorize

                                                // Public web pages
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
                                                                "/index/*/images/**")
                                                .permitAll()

                                                // Authenticated users
                                                .requestMatchers(
                                                                "/addReservation/**",
                                                                "/profile/**",
                                                                "/editProfile/**",
                                                                "/editProfile/*/images",
                                                                "/editProfileImage/**",
                                                                "/postApartmentReviews/**",
                                                                "/replace/**")
                                                .hasAnyRole("USER")

                                                // Client-only pages
                                                .requestMatchers(
                                                                "/addReservation/**",
                                                                "/clientReservations",
                                                                "/clientReservations/**",
                                                                "/reservationInfo/**",
                                                                "/cancelReservation/**",
                                                                "/postApartmentReviews/**",
                                                                "/loadMoreReservations/**")
                                                .hasAnyRole("CLIENT")

                                                // Manager-only pages
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
                                                                "/loadMoreApartmentsManagerView/**")
                                                .hasAnyRole("MANAGER")

                                                // Admin-only pages
                                                .requestMatchers(
                                                                "/managerValidation",
                                                                "/acceptance/**",
                                                                "/rejection/**")
                                                .hasAnyRole("ADMIN"))
                                .formLogin(formLogin -> formLogin
                                .usernameParameter("nick")
                                                .loginPage("/login")
                                                .failureUrl("/loginError")
                                                .defaultSuccessUrl("/profile", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login")
                                                .permitAll())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                                .maximumSessions(1));
                                // CSRF is enabled by default for web routes using forms

                return http.build();
        }
}
