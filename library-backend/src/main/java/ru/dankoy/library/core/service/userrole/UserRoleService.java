package ru.dankoy.library.core.service.userrole;

import java.util.Optional;
import ru.dankoy.library.core.domain.UserRole;

public interface UserRoleService {

  Optional<UserRole> findByRole(String role);

}
