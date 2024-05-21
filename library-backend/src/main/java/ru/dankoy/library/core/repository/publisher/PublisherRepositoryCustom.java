package ru.dankoy.library.core.repository.publisher;

import ru.dankoy.library.core.domain.Publisher;

public interface PublisherRepositoryCustom {

  void deleteAndCheckEditions(Publisher publisher);
}
