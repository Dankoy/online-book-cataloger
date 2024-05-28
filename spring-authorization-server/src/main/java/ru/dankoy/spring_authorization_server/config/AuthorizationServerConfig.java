package ru.dankoy.spring_authorization_server.config;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationValidator;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

// look how to configure in spring boot manner https://www.baeldung.com/spring-security-oauth-auth-server

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {


  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {

    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
    http
//        .csrf(Customizer.withDefaults())
//        .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
        .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
        .exceptionHandling((exceptions) -> exceptions
            .defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
            )
        )
        .formLogin(Customizer.withDefaults())
    ;

    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("gateway")
        .clientSecret("$2a$10$ur3iG0Do9xyrqTWJHGZQJ.uY8mpxernuB7sO1ZPWUZMBgsaW3ugmy") //secret
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri("http://localhost:8080/login/oauth2/code/gateway")
        .scope(OidcScopes.OPENID)
        .scope("library.read")
//        .scope("library.write")
        .build();

    return new InMemoryRegisteredClientRepository(registeredClient);
  }

//  private Consumer<List<AuthenticationProvider>> configureAuthenticationValidator() {
//    return (authenticationProviders) ->
//        authenticationProviders.forEach((authenticationProvider) -> {
//          if (authenticationProvider instanceof OAuth2AuthorizationCodeRequestAuthenticationProvider) {
//            Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> authenticationValidator =
//                // Override default redirect_uri validator
//                new CustomRedirectUriValidator()
//                    // Reuse default scope validator
//                    .andThen(OAuth2AuthorizationCodeRequestAuthenticationValidator.DEFAULT_SCOPE_VALIDATOR);
//
//            ((OAuth2AuthorizationCodeRequestAuthenticationProvider) authenticationProvider)
//                .setAuthenticationValidator(authenticationValidator);
//          }
//        });
//  }
//
//  static class CustomRedirectUriValidator implements
//      Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> {
//
//    @Override
//    public void accept(OAuth2AuthorizationCodeRequestAuthenticationContext authenticationContext) {
//      OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication =
//          authenticationContext.getAuthentication();
//      RegisteredClient registeredClient = authenticationContext.getRegisteredClient();
//      String requestedRedirectUri = authorizationCodeRequestAuthentication.getRedirectUri();
//
//      // Use exact string matching when comparing client redirect URIs against pre-registered URIs
//      if (!registeredClient.getRedirectUris().contains(requestedRedirectUri)) {
//        OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
//        throw new OAuth2AuthorizationCodeRequestAuthenticationException(error, null);
//      }
//    }
//  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  private static RSAKey generateRsa() {
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
  }

  private static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }

  @Bean
  public AuthorizationServerSettings providerSettings() {
    return AuthorizationServerSettings.builder()
        .issuer("http://localhost:9000")
        .build();
  }

}
