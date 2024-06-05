package ru.dankoy.springcloudgateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(
      ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {

    // Authenticate through configured OpenID Provider
    http.oauth2Login(Customizer.withDefaults());

    // Also logout at the OpenID Connect provider
    http.logout(
        logout ->
            logout.logoutSuccessHandler(
                new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));

    // Require authentication for all requests
    http.authorizeExchange(
        auth -> auth.pathMatchers("/api/v1/register").permitAll().anyExchange().authenticated());

    // Allow showing /home within a frame
    http.headers(h -> h.frameOptions(f -> f.mode(Mode.SAMEORIGIN)));

    // Disable CSRF in the gateway to prevent conflicts with proxied service CSRF
    http.csrf(CsrfSpec::disable);

    return http.build();
  }
}
