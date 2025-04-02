package online_market.products_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {
    
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
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("products-service-api/products-media/{name-media}").permitAll()
                        .requestMatchers("products-service-api/products/products-by-list-id")
                        .hasAuthority("SCOPE_view_products")
                        .requestMatchers(HttpMethod.GET).hasAuthority("SCOPE_view_products")
                        .requestMatchers(HttpMethod.POST).hasAuthority("SCOPE_edit_products")
                        .requestMatchers(HttpMethod.PATCH).hasAuthority("SCOPE_edit_products")
                        .requestMatchers(HttpMethod.DELETE).hasAuthority("SCOPE_edit_products")
                        .anyRequest().denyAll())
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
