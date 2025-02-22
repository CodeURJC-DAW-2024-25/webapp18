@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserSecurityService userDetailService;

    private static final String[] PUBLIC_ENDPOINTS = {
        "/login", "/loginerror", "/register", "/nickTaken", "/error",
        "/index", "/indexsearch", "/returnmainpage", "/captcha",
        "/notfounderror/**", "/apartmentReviews/**", "/apartmentinformation/**",
        "/notRooms/**", "/loadMoreApartments/**", "/loadMoreReviews/**"
    };

    private static final String[] STATIC_RESOURCES = {
        "/css/**", "/js/**", "/fonts/**", "/images/**", "/img/**",
        "/profile/*/images/", "/static/images/**", "/index/*/images/**"
    };

    private static final String[] USER_ENDPOINTS = {
        "/profile/**", "/editprofile/**", "/editprofile/*/images",
        "/editprofileimage/**", "/postapartmentReviews/**", "/replace/**"
    };

    private static final String[] CLIENT_ENDPOINTS = {
        "/clientreservations/**", "/reservationInfo/**", "/addReservation/**",
        "/cancelReservation/**", "/postapartmentReviews/**", "/loadMoreReservations/**"
    };

    private static final String[] MANAGER_ENDPOINTS = {
        "/editapartment/**", "/viewapartmentsmanager", "/deleteApartment/**",
        "/clientlist/**", "/chartsmanager", "/addApartment/**", "/testChart/**",
        "/application/**", "/editapartmentimage/**", "/addApartmentPhoto/**",
        "/selectapartmentimage/**", "/createApartment/**", "/chartsManager",
        "/addApartment", "/loadMoreApartmentsManagerView/**"
    };

    private static final String[] ADMIN_ENDPOINTS = {
        "/managerlist", "/chartsadmin", "/managervalidation",
        "/acceptance/**", "/rejection/**"
    };

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
            // Security Headers
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
                .xssProtection(xss -> xss.disable())
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self' https://cdnjs.cloudflare.com; style-src 'self' https://fonts.googleapis.com https://cdnjs.cloudflare.com 'unsafe-inline'; font-src 'self' https://fonts.gstatic.com https://cdnjs.cloudflare.com; img-src 'self' data:;"))
                .referrerPolicy(referrer -> referrer
                    .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)))
            
            // CSRF Protection
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(PUBLIC_ENDPOINTS)
                .ignoringRequestMatchers(STATIC_RESOURCES))
            
            // Authorization Rules
            .authorizeHttpRequests(authorize -> authorize
                // Public resources
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(STATIC_RESOURCES).permitAll()
                
                // Role-based access
                .requestMatchers(USER_ENDPOINTS).hasAnyRole("USER")
                .requestMatchers(CLIENT_ENDPOINTS).hasAnyRole("CLIENT")
                .requestMatchers(MANAGER_ENDPOINTS).hasAnyRole("MANAGER")
                .requestMatchers(ADMIN_ENDPOINTS).hasAnyRole("ADMIN")
                
                // Any other request needs authentication
                .anyRequest().authenticated()
            )
            
            // Login Configuration
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .failureUrl("/loginerror")
                .defaultSuccessUrl("/profile")
                .permitAll())
            
            // Logout Configuration
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll());

        return http.build();
    }
}