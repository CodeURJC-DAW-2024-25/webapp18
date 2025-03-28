package es.codeurjc.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    private static final Logger LOG = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip JWT filter for public endpoints like login and registration
        if (path.equals("/api/v1/users/login") || path.equals("/api/v1/users/")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = getJwtToken(request, true);
            
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                
                String nick = jwtTokenProvider.getUsername(token);
                
                UserDetails userDetails = userDetailsService.loadUserByUsername(nick);
                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            LOG.error("Exception processing JWT Token", ex);
        }

        filterChain.doFilter(request, response);
    }    

    private String getJwtToken(HttpServletRequest request, boolean fromCookie) {
        
        if (fromCookie) {
            return getJwtFromCookie(request);
        } else {
            return getJwtFromRequest(request);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        
        String bearerToken = request.getHeader("Authorization");
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        
            String accessToken = bearerToken.substring(7);
            if (accessToken == null) {
                return null;
            }

            return SecurityCipher.decrypt(accessToken);
        }
        return null;
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        
        Cookie[] cookies = request.getCookies();
        
        if (cookies == null) {
            return "";
        }
        
        for (Cookie cookie : cookies) {
            if (JwtCookieManager.ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                String accessToken = cookie.getValue();
                if (accessToken == null) {
                    return null;
                }

                return SecurityCipher.decrypt(accessToken);
            }
        }
        return null;
    }
}
