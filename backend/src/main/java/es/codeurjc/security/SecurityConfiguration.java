package es.codeurjc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

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

        @Bean
	@Order(1)
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		
		http.authenticationProvider(authenticationProvider());
		
		http
			.securityMatcher("/api/**")
			.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

        http
			.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/v1/**, \"/swagger-ui/**\", \"/v3/api-docs/**\"")
                                //pendiente de añadir los permisoa a las rutas
                                .permitAll()
                                .anyRequest().authenticated());

        // Disable Form login Authentication
        http.formLogin(formLogin -> formLogin.disable());

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf(csrf -> csrf.disable());

        // Disable Basic Authentication
        http.httpBasic(httpBasic -> httpBasic.disable());

        // Stateless session
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	// Add JWT Token filter
	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	return http.build();
	}




        @Bean
        @Order(2)
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authenticationProvider(authenticationProvider())
                                .authorizeHttpRequests(authorize -> authorize
                                                // Endpoints públicos
                                                .requestMatchers(
                                                                "/api/**",
                                                                "/api/v1/users/login",
                                                                "/api/v1/users/",
                                                                "/api/v1/users/all",
                                                                "/api/v1/users/{id}",
                                                                "/api/v1/users/{id}/image",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html",
                                                                "/api-docs/**")
                                                .permitAll()

                                                // Endpoints que requieren autenticación
                                                .requestMatchers(
                                                                "/api/v1/users/profile",
                                                                "/api/v1/users/{id}/application",
                                                                "/api/v1/users/{id}",
                                                                "/api/v1/users/{id}/profile-image")
                                                .authenticated()

                                                // Endpoints restringidos solo a ADMIN
                                                .requestMatchers(
                                                                "/api/v1/users/{id}/validate",
                                                                "/api/v1/users/{id}/reject")
                                                .hasRole("ADMIN")

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
                                                                "/index/*/images/**")
                                                .permitAll()

                                                // Páginas accesibles para usuarios autenticados
                                                .requestMatchers(
                                                                "/addReservation/**",
                                                                "/profile/**",
                                                                "/editProfile/**",
                                                                "/editProfile/*/images",
                                                                "/editProfileImage/**",
                                                                "/postApartmentReviews/**",
                                                                "/replace/**")
                                                .hasAnyRole("USER")

                                                // Páginas accesibles para clientes
                                                .requestMatchers(
                                                                "/addReservation/**",
                                                                "/clientReservations",
                                                                "/clientReservations/**",
                                                                "/reservationInfo/**",
                                                                "/cancelReservation/**",
                                                                "/postApartmentReviews/**",
                                                                "/loadMoreReservations/**")
                                                .hasAnyRole("CLIENT")

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
                                                                "/loadMoreApartmentsManagerView/**")
                                                .hasAnyRole("MANAGER")

                                                // Páginas accesibles solo para administradores
                                                .requestMatchers(
                                                                "/managerValidation",
                                                                "/acceptance/**",
                                                                "/rejection/**")
                                                .hasAnyRole("ADMIN"))
                                .formLogin(formLogin -> formLogin
                                                .loginPage("/login")
                                                .failureUrl("/loginError")
                                                .defaultSuccessUrl("/profile", true)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login")
                                                .permitAll())
                                .sessionManagement(session -> session
                                // Asegura que la sesión se cree si es necesaria
                                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) 
                                                .maximumSessions(1));

                return http.build();
        }
}
