package ru.dankoy.spring_authorization_server.core.dto.user;

import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.dankoy.spring_authorization_server.core.domain.User;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

  private String username;
  private String password;

  public static User fromDTO(UserRegisterDTO dto) {
    return new User(null, dto.username, dto.getPassword(), true, true, true, true, new HashSet<>());
  }
}
