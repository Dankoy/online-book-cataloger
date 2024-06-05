package ru.dankoy.spring_authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class DefaultSecurityConfig {

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            a -> a.requestMatchers("/api/v1/register").permitAll().anyRequest().authenticated())
        .formLogin(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Генерирует сам соль и хранит в той же строке, что и пароль в бд, самим не надо ничего делать
    // Если не добавить, то ошибка - There is no PasswordEncoder mapped for the id "null"
    return new BCryptPasswordEncoder(10);
  }
}
