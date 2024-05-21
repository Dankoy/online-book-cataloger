package ru.dankoy.libraryauth.core.repository.userrole;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dankoy.libraryauth.core.domain.UserRole;

public interface UserRoleRepository extends MongoRepository<UserRole, String> {

  Optional<UserRole> findByRole(String role);

}
