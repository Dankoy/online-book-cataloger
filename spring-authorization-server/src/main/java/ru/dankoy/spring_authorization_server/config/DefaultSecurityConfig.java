package ru.dankoy.spring_authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
public class DefaultSecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

    // todo: make own sec config
    http
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .anyRequest()
                .authenticated()
        )
        .formLogin(Customizer.withDefaults())
    ;

    return http.build();
  }


  //todo: make it work with DB
  @Bean
  UserDetailsService users() {
    UserDetails user = User.withDefaultPasswordEncoder()
        .username("admin")
        .password("password")
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

}
