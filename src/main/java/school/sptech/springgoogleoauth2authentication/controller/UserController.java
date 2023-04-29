package school.sptech.springgoogleoauth2authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.springgoogleoauth2authentication.domain.User;
import school.sptech.springgoogleoauth2authentication.service.UserService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<User> createUser(JwtAuthenticationToken principal) {
    return ResponseEntity.ok(this.userService.create(principal));
  }

  @GetMapping("user-details")
  public ResponseEntity<Object> userDetails(JwtAuthenticationToken principal) {
    return ResponseEntity.ok(principal.getToken());
  }
}
