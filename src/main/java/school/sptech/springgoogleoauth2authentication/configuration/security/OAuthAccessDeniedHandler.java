package school.sptech.springgoogleoauth2authentication.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import school.sptech.springgoogleoauth2authentication.model.AccessDeniedModel;

import java.io.IOException;
import java.util.List;

public class OAuthAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    List<String> authorities = authentication.getAuthorities().stream()
        .map(Object::toString)
        .toList();

    if (!authorities.contains("ROLE_USER")) {
      AccessDeniedModel accessDeniedModel = new AccessDeniedModel("User not registered", "ROLE_USER");
      response.getWriter().write(new ObjectMapper().writeValueAsString(accessDeniedModel));
      response.setContentType("application/json");
    }

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
  }
}
