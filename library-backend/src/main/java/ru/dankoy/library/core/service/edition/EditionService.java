package ru.dankoy.library.core.service.edition;

import java.util.Optional;
import java.util.Set;
import ru.dankoy.library.core.domain.Edition;

public interface EditionService {

  Set<Edition> findAllByWorkId(String workId);

  Optional<Edition> findById(String id);

  Edition create(Edition edition);

  void deleteById(String id);

  Edition update(Edition edition);
}
