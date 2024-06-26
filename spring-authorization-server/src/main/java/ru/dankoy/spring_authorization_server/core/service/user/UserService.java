package ru.dankoy.spring_authorization_server.core.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.dankoy.spring_authorization_server.core.domain.User;

public interface UserService extends UserDetailsService {

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  void create(User user);
}
