package ru.pet.socialnetwork.config.jwt;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import ru.pet.socialnetwork.service.userdetails.CustomUserDetailsService;

import java.util.Arrays;

import static ru.pet.socialnetwork.constants.SecurityConstants.*;
import static ru.pet.socialnetwork.constants.UserRolesConstants.ADMIN;
import static ru.pet.socialnetwork.constants.UserRolesConstants.USER;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTSecurityConfig {

    private final JWTTokenFilter jwtTokenFilter;
    private final CustomUserDetailsService customUserDetailsService;


    public JWTSecurityConfig(JWTTokenFilter jwtTokenFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.customUserDetailsService = customUserDetailsService;
    }


    @Bean
    public HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        return firewall;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
                        .requestMatchers(POSTS_WHITE_LIST.toArray(String[]::new)).hasRole(USER)
                        .requestMatchers(SUBSCRIPTIONS_WHITE_LIST.toArray(String[]::new)).hasRole(USER)
                        .requestMatchers(MESSAGES_WHITE_LIST.toArray(String[]::new)).hasRole(USER)
                        .requestMatchers(USERS_PERMISSION_LIST.toArray(String[]::new)).hasRole(ADMIN)
//                        .requestMatchers(POSTS_PERMISSION_LIST.toArray(String[]::new)).hasRole(ADMIN)
//                        .requestMatchers(SUBSCRIPTIONS_PERMISSION_LIST.toArray(String[]::new)).hasRole(ADMIN)
//                        .requestMatchers(MESSAGES_PERMISSION_LIST.toArray(String[]::new)).hasRole(ADMIN)
                )
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()))
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(customUserDetailsService)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }
}
