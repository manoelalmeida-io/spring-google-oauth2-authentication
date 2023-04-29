package school.sptech.springgoogleoauth2authentication.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import school.sptech.springgoogleoauth2authentication.configuration.security.OAuthAccessDeniedHandler;
import school.sptech.springgoogleoauth2authentication.configuration.security.OAuthAuthenticationConverter;
import school.sptech.springgoogleoauth2authentication.repository.UserRepository;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final UserRepository userRepository;

  @Bean
  SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/users/**", "POST")).authenticated()
            .anyRequest().hasAuthority("ROLE_USER"))
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .jwtAuthenticationConverter(new OAuthAuthenticationConverter(userRepository))
            ))
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler());

    return http.build();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler(){
    return new OAuthAccessDeniedHandler();
  }
}

