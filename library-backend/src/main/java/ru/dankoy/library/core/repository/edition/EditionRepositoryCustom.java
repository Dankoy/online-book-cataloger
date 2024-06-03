package ru.dankoy.library.core.repository.edition;

import ru.dankoy.library.core.domain.Edition;

public interface EditionRepositoryCustom {

  void deleteAndCheckNotesShelvesWorks(Edition edition);
}
