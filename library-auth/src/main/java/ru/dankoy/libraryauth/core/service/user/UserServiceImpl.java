package ru.dankoy.libraryauth.core.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dankoy.libraryauth.core.domain.User;
import ru.dankoy.libraryauth.core.exceptions.RegistrationException;
import ru.dankoy.libraryauth.core.repository.user.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }


  @Override
  public void create(User user) {

    var optionalUser = userRepository.findByUsername(user.getUsername());

    if (optionalUser.isEmpty()) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
    } else {
      throw new RegistrationException(
          String.format("User with login '%s' already exists", user.getUsername()));
    }

  }
}
