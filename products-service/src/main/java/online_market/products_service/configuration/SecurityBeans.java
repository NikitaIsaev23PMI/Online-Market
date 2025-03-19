package online_market.products_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.GET).hasAuthority("SCOPE_view_products")
                        .requestMatchers(HttpMethod.POST).hasAuthority("SCOPE_edit_products")
                        .requestMatchers(HttpMethod.PATCH).hasAuthority("SCOPE_edit_products")
                        .requestMatchers(HttpMethod.DELETE).hasAuthority("SCOPE_edit_products")
                        .anyRequest().denyAll())
                .build();
    }
}
