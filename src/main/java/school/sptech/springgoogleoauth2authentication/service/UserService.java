package school.sptech.springgoogleoauth2authentication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import school.sptech.springgoogleoauth2authentication.domain.User;
import school.sptech.springgoogleoauth2authentication.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User create(JwtAuthenticationToken principal) {
    User user = userRepository.save(this.createUser(principal));
    log.info("User created: {}", user);
    return user;
  }

  private User createUser(JwtAuthenticationToken principal) {
    Jwt jwt = principal.getToken();

    User user = new User();
    user.setName(jwt.getClaimAsString("name"));
    user.setEmail(jwt.getClaimAsString("email"));
    user.setSub(jwt.getClaimAsString("sub"));
    user.setRole("ROLE_USER");

    return user;
  }
}
