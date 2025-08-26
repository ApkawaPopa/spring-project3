package minchakov.arkadii.springproject3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(AbstractHttpConfigurer::disable)
                   .csrf(AbstractHttpConfigurer::disable)
                   .formLogin(AbstractHttpConfigurer::disable)
                   .sessionManagement(session ->
                       session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .authorizeHttpRequests(customizer ->
                       customizer
                           .requestMatchers(HttpMethod.GET, "/measurements", "/measurements/rainyDaysCount")
                           .permitAll()
                           .requestMatchers(HttpMethod.POST, "/measurements/add", "/sensors/registration")
                           .permitAll()
                           .anyRequest()
                           .denyAll()
                   )
                   .build();
    }
}
