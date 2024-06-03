package ru.dankoy.spring_authorization_server.core.service.userrole;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dankoy.spring_authorization_server.core.domain.UserRole;
import ru.dankoy.spring_authorization_server.core.repository.userrole.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

  private final UserRoleRepository userRoleRepository;

  @Override
  public Optional<UserRole> findByRole(String role) {
    return userRoleRepository.findByRole(role);
  }
}
