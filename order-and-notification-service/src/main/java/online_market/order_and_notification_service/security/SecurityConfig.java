package online_market.order_and_notification_service.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${keycloak.realms.uri.sellers}")
    private String sellerRealmUrl;

    @Value("${keycloak.realms.uri.buyers}")
    private String buyerRealmUrl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder())))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder1 = JwtDecoders.fromIssuerLocation(buyerRealmUrl);
        NimbusJwtDecoder jwtDecoder2 = JwtDecoders.fromIssuerLocation(sellerRealmUrl);

        return token -> {
            try {
                return jwtDecoder1.decode(token);
            } catch (JwtException e) {
                return jwtDecoder2.decode(token);
            }
        };
    }
}
