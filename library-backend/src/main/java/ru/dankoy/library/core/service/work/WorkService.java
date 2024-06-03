package ru.dankoy.library.core.service.work;

import java.util.List;
import java.util.Optional;
import ru.dankoy.library.core.domain.Author;
import ru.dankoy.library.core.domain.Genre;
import ru.dankoy.library.core.domain.Work;

public interface WorkService {

  List<Work> findAllByGenreName(Genre genre);

  List<Work> findAllByAuthorId(Author author);

  List<Work> findAll();

  Optional<Work> getById(String id);

  Work insert(Work work);

  //  @Retry(name = "bookService")
  Work update(Work work);

  void deleteById(String id);

  long count();

  void updateMultiple(List<Work> works);
}
