package ru.dankoy.library.config.security;


import com.jayway.jsonpath.JsonPath;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Component;
import ru.dankoy.library.core.exceptions.api.security.AccessDeniedHandlerRest;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http,
      Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter)
      throws Exception {

    http

        // State-less session (state in access-token only)
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Disable CSRF because of state-less session-management
        .csrf(CsrfConfigurer::disable)

        .exceptionHandling(e -> e.accessDeniedHandler(new AccessDeniedHandlerRest()))

        .authorizeHttpRequests(a -> a
            .requestMatchers("/", "/error", "403", "/webjars/**")
            .permitAll()

            .requestMatchers("/actuator/health/readiness", "/actuator/health/liveness",
                "/v3/api-docs/**").permitAll()

            .requestMatchers(HttpMethod.GET, "/api/v1/work", "/api/v1/work/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
                Authority.READER.name()) // для любой роли доступен.

            .requestMatchers(HttpMethod.POST, "/api/v1/work")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.PUT, "/api/v1/work/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.DELETE, "/api/v1/work/*")
            .hasAnyRole(Authority.ADMIN.name())

            // editions
            .requestMatchers(HttpMethod.POST, "/api/v1/work/*/edition")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.GET, "/api/v1/work/*/edition")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
                Authority.READER.name())

            .requestMatchers(HttpMethod.PUT, "/api/v1/edition/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.DELETE, "/api/v1/edition/*")
            .hasAnyRole(Authority.ADMIN.name())

            // commentary
            .requestMatchers(HttpMethod.GET, "/api/v1/work/*/commentary")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
                Authority.READER.name()) // для любой роли доступен.

            .requestMatchers(HttpMethod.POST, "/api/v1/work/*/commentary")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.PUT, "/api/v1/commentary/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.DELETE, "/api/v1/commentary/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            // genre
            .requestMatchers(HttpMethod.GET, "/api/v1/genre")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
                Authority.READER.name()) // для любой роли доступен.

            .requestMatchers(HttpMethod.PUT, "/api/v1/genre?oldGenre=*")
            .hasAnyRole(Authority.ADMIN.name())

            .requestMatchers(HttpMethod.DELETE, "/api/v1/genre")
            .hasAnyRole(Authority.ADMIN.name())

            // note
            .requestMatchers(HttpMethod.GET, "/api/v1/note", "/api/v1/note/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
                Authority.READER.name()) // для любой роли доступен.

            .requestMatchers(HttpMethod.POST, "/api/v1/note")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.PUT, "/api/v1/note/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.DELETE, "/api/v1/note/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            // publisher
            .requestMatchers(HttpMethod.GET, "/api/v1/publisher", "/api/v1/publisher/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
                Authority.READER.name()) // для любой роли доступен.

            .requestMatchers(HttpMethod.POST, "/api/v1/publisher")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.PUT, "/api/v1/publisher/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.DELETE, "/api/v1/publisher/*")
            .hasAnyRole(Authority.ADMIN.name())

            // shelf
            .requestMatchers(HttpMethod.GET, "/api/v1/shelf", "/api/v1/shelf/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
                Authority.READER.name()) // для любой роли доступен.

            .requestMatchers(HttpMethod.POST, "/api/v1/shelf")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.PUT, "/api/v1/shelf/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .requestMatchers(HttpMethod.DELETE, "/api/v1/shelf/*")
            .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())

            .anyRequest().authenticated()
        )
        .exceptionHandling(e
            -> e.authenticationEntryPoint(
            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .logout(l -> l.logoutSuccessUrl("/").permitAll())
        .oauth2ResourceServer(
            oa -> oa.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
    ;

    return http.build();
  }


  @RequiredArgsConstructor
  static class JwtGrantedAuthoritiesConverter implements
      Converter<Jwt, Collection<? extends GrantedAuthority>> {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Collection<? extends GrantedAuthority> convert(Jwt jwt) {
      var claims = jwt.getClaims();
      if (claims.containsKey("roles")) {
        var roles = (List<String>) claims.get("roles");
        return roles.stream()
            .map(s -> "ROLE_" + s)
            .map(SimpleGrantedAuthority::new)
            .map(GrantedAuthority.class::cast)
            .collect(Collectors.toSet());
      } else {
        return Collections.emptyList();
      }
    }

  }

  @Component
  @RequiredArgsConstructor
  static class SpringAddonsJwtAuthenticationConverter implements
      Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
      final var authorities = new JwtGrantedAuthoritiesConverter().convert(jwt);
      final String username = JsonPath.read(jwt.getClaims(), "sub");
      return new JwtAuthenticationToken(jwt, authorities, username);
    }
  }

}
