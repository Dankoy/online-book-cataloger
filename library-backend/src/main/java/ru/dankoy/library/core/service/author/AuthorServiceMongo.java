package ru.dankoy.library.core.service.author;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dankoy.library.core.domain.Author;
import ru.dankoy.library.core.domain.Work;
import ru.dankoy.library.core.exceptions.Entity;
import ru.dankoy.library.core.exceptions.EntityNotFoundException;
import ru.dankoy.library.core.repository.author.AuthorRepository;
import ru.dankoy.library.core.service.work.WorkService;

@Service
@RequiredArgsConstructor
public class AuthorServiceMongo implements AuthorService {

  private final AuthorRepository authorRepository;

  private final WorkService workService;

  @Override
  public List<Author> getAll() {
    return authorRepository.findAll();
  }

  @Override
  public Optional<Author> getById(String id) {
    return authorRepository.findById(id);
  }

  @Override
  public Author insertOrUpdate(Author author) {
    return authorRepository.save(author);
  }

  @Override
  public void deleteById(String id) {
    var optional = authorRepository.findById(id);
    var author = optional.orElseThrow(() -> new EntityNotFoundException(id, Entity.AUTHOR));

    List<Work> works = workService.findAllByAuthorId(author);

    works.forEach(b -> b.getAuthors().remove(author));

    workService.updateMultiple(works);

    authorRepository.delete(author);
  }

  @Override
  public long count() {
    return authorRepository.count();
  }
}
