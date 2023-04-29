package school.sptech.springgoogleoauth2authentication.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import school.sptech.springgoogleoauth2authentication.domain.User;
import school.sptech.springgoogleoauth2authentication.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OAuthAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final UserRepository userRepository;

  public AbstractAuthenticationToken convert(Jwt jwt) {
    String sub = jwt.getClaim("sub");

    Optional<User> user = userRepository.findBySub(sub);

    List<GrantedAuthority> authorities = user.map(item -> (GrantedAuthority) item::getRole)
        .map(List::of).orElse(new ArrayList<>());

    log.info("User authorities: {}", authorities.stream().map(GrantedAuthority::getAuthority).toList());

    return new JwtAuthenticationToken(jwt, authorities);
  }
}
