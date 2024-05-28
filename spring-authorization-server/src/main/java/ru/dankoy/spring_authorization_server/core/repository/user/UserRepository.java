package ru.dankoy.spring_authorization_server.core.repository.user;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dankoy.spring_authorization_server.core.domain.User;


public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByUsername(String username);

}
