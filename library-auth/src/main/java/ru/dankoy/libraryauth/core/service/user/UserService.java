package ru.dankoy.libraryauth.core.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.dankoy.libraryauth.core.domain.User;

public interface UserService extends UserDetailsService {

  @Override
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  void create(User user);
}
