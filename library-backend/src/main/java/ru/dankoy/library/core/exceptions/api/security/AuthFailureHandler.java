package ru.dankoy.library.core.exceptions.api.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.dankoy.library.core.exceptions.api.ApiError;

@Slf4j
@NoArgsConstructor
public class AuthFailureHandler {

  public void formatResponse(HttpServletResponse response, Exception ex) throws IOException {

    response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());

    ApiError apiResponse =
        new ApiError(LocalDateTime.now(), HttpStatus.FORBIDDEN, "Access Denied", details);

    try {
      var responseBody = mapper.writeValueAsString(apiResponse);

      response.getWriter().write(responseBody);

    } catch (JsonProcessingException jsonProcessingException) {
      log.error(
          "Json parsing error - {}", jsonProcessingException.getMessage(), jsonProcessingException);
    }
  }
}
