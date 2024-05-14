package core.bookie.security.configs;


import core.bookie.security.TokenFilter;
import core.bookie.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static core.bookie.utils.AntPattern.ADMIN_ENDPOINTS;
import static core.bookie.utils.AntPattern.UNSECURED_ENDPOINTS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilter {


    private final TokenFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenService jwtService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(
                        auth -> auth.requestMatchers( UNSECURED_ENDPOINTS ).permitAll()
                                .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                                .anyRequest().authenticated()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider)


                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}