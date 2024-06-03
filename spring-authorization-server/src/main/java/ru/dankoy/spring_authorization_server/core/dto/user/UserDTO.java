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
public class UserDTO {

  private String id;

  public static User fromDTO(UserDTO dto) {
    return new User(dto.id, null, null, false, false, false, false, new HashSet<>());
  }
}
