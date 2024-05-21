package ru.dankoy.libraryauth.core.service.userrole;

import java.util.Optional;
import ru.dankoy.libraryauth.core.domain.UserRole;

public interface UserRoleService {

  Optional<UserRole> findByRole(String role);

}
