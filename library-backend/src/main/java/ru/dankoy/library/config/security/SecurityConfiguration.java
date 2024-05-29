package ru.dankoy.library.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(a
                -> a.requestMatchers("/", "/error", "/webjars/**")
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/api/v1/work", "/api/v1/work/*")
                // SCOPE_ фрэймворк сам добавляет. Что бы работаеть с ролями, надо их достать и положить в GrantedAuthorities рядом со скоупом
                .hasAnyAuthority("SCOPE_library.read")
//                .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
//                    Authority.READER.name()) // для любой роли доступен.

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
        .oauth2ResourceServer(oa -> oa.jwt(Customizer.withDefaults()))
    ;

    return http.build();
  }

//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    // hasAnyAuthority - позволяет работать с ролями без префикса ROLE_
//    // hasAnyRole - в бд обязательно должны быть роли с префиксом ROLE_
//
////    http
////        .csrf().disable()
////        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        .and()
////        .authorizeHttpRequests(authorize ->
////                authorize
////                    .antMatchers("/**")
////                    .permitAll()
////
////                    // work
////                    .antMatchers(HttpMethod.GET, "/api/v1/work", "/api/v1/work/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
////                        Authority.READER.name()) // для любой роли доступен.
////
////                    .antMatchers(HttpMethod.POST, "/api/v1/work")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.PUT, "/api/v1/work/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.DELETE, "/api/v1/work/*")
////                    .hasAnyRole(Authority.ADMIN.name())
////
////                    // editions
////                    .antMatchers(HttpMethod.POST, "/api/v1/work/*/edition")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.GET, "/api/v1/work/*/edition")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
////                        Authority.READER.name())
////
////                    .antMatchers(HttpMethod.PUT, "/api/v1/edition/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.DELETE, "/api/v1/edition/*")
////                    .hasAnyRole(Authority.ADMIN.name())
////
////                    // commentary
////                    .antMatchers(HttpMethod.GET, "/api/v1/work/*/commentary")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
////                        Authority.READER.name()) // для любой роли доступен.
////
////                    .antMatchers(HttpMethod.POST, "/api/v1/work/*/commentary")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.PUT, "/api/v1/commentary/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.DELETE, "/api/v1/commentary/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    // genre
////                    .antMatchers(HttpMethod.GET, "/api/v1/genre")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
////                        Authority.READER.name()) // для любой роли доступен.
////
////                    .antMatchers(HttpMethod.PUT, "/api/v1/genre?oldGenre=*")
////                    .hasAnyRole(Authority.ADMIN.name())
////
////                    .antMatchers(HttpMethod.DELETE, "/api/v1/genre")
////                    .hasAnyRole(Authority.ADMIN.name())
////
////                    // note
////                    .antMatchers(HttpMethod.GET, "/api/v1/note", "/api/v1/note/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
////                        Authority.READER.name()) // для любой роли доступен.
////
////                    .antMatchers(HttpMethod.POST, "/api/v1/note")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.PUT, "/api/v1/note/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.DELETE, "/api/v1/note/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    // publisher
////                    .antMatchers(HttpMethod.GET, "/api/v1/publisher", "/api/v1/publisher/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
////                        Authority.READER.name()) // для любой роли доступен.
////
////                    .antMatchers(HttpMethod.POST, "/api/v1/publisher")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.PUT, "/api/v1/publisher/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.DELETE, "/api/v1/publisher/*")
////                    .hasAnyRole(Authority.ADMIN.name())
////
////                    // shelf
////                    .antMatchers(HttpMethod.GET, "/api/v1/shelf", "/api/v1/shelf/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name(),
////                        Authority.READER.name()) // для любой роли доступен.
////
////                    .antMatchers(HttpMethod.POST, "/api/v1/shelf")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.PUT, "/api/v1/shelf/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    .antMatchers(HttpMethod.DELETE, "/api/v1/shelf/*")
////                    .hasAnyRole(Authority.ADMIN.name(), Authority.OPERATOR.name())
////
////                    // register
////                    .antMatchers("/api/v1/register").permitAll()
////                    .anyRequest().authenticated()
////            // обязательно любой запрос должен быть аутентифицирован
////
////        )
////        .formLogin();
//
//    return http.build();
//  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // Генерирует сам соль и хранит в той же строке, что и пароль в бд, самим не надо ничего делать
    // Если не добавить, то ошибка - There is no PasswordEncoder mapped for the id "null"
    return new BCryptPasswordEncoder(10);
  }

}
