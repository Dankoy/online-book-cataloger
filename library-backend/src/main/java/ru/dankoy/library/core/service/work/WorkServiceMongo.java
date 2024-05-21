package ru.dankoy.library.core.service.work;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.dankoy.library.core.aspects.AddCreatedMetadata;
import ru.dankoy.library.core.domain.Author;
import ru.dankoy.library.core.domain.Genre;
import ru.dankoy.library.core.domain.Work;
import ru.dankoy.library.core.repository.work.WorkRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkServiceMongo implements WorkService {

  private final WorkRepository workRepository;


  //  @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackListBooksByGenre")
  @Override
  public List<Work> findAllByGenreName(Genre genre) {

    return workRepository.findBookByGenres(genre.getName());

  }

  //  @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackListBooksByAuthor")
  @Override
  public List<Work> findAllByAuthorId(Author author) {

    return workRepository.findBookByAuthorsId(author.getId());

  }

  //  @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackListBooks")
  @Override
  public List<Work> findAll() {
    return workRepository.findAll();
  }

  //  @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackOptionalBook")
  @Override
  public Optional<Work> getById(String id) {
    return workRepository.findById(id);
  }

//  @Retry(name = "bookService")
  @Override
  public Work insert(Work work) {
    return workRepository.saveAndCheckAuthors(work);
  }

//  @Retry(name = "bookService")
  @Override
  @AddCreatedMetadata
  public Work update(Work work) {
    return workRepository.saveAndCheckAuthors(work);
  }

//  @Retry(name = "bookService")
  @Override
  public void deleteById(String id) {
    workRepository.deleteByWorkId(id);
  }

  //  @CircuitBreaker(name = "bookService", fallbackMethod = "fallbackCount")
  @Override
  public long count() {
    return workRepository.count();
  }


  @Override
  public void updateMultiple(List<Work> works) {
    workRepository.saveAll(works);
  }


}
