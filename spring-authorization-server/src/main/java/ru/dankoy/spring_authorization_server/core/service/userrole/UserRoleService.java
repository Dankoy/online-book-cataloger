package ru.dankoy.spring_authorization_server.core.service.userrole;

import java.util.Optional;
import ru.dankoy.spring_authorization_server.core.domain.UserRole;

public interface UserRoleService {

  Optional<UserRole> findByRole(String role);

}
