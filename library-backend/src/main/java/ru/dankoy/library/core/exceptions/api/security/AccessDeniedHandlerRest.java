package ru.dankoy.library.core.exceptions.api.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class AccessDeniedHandlerRest implements AccessDeniedHandler {

  //Access Denied / unauthorized has handle method when failures occur

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    response.setStatus(HttpStatus.FORBIDDEN.value());
    new AuthFailureHandler().formatResponse(response, accessDeniedException);

  }
}
