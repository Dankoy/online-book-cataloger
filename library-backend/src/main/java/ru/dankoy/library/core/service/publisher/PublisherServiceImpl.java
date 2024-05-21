package ru.dankoy.library.core.service.publisher;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dankoy.library.core.aspects.AddCreatedMetadata;
import ru.dankoy.library.core.domain.Publisher;
import ru.dankoy.library.core.repository.publisher.PublisherRepository;


@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

  private final PublisherRepository publisherRepository;

  @Override
  public Optional<Publisher> findById(String id) {
    return publisherRepository.findById(id);
  }

  @Override
  public List<Publisher> findAll() {
    return publisherRepository.findAll();
  }

  @Override
  public Publisher create(Publisher publisher) {
    return publisherRepository.save(publisher);
  }

  @Override
  public void deleteById(String id) {
    var optionalPublisher = publisherRepository.findById(id);
    optionalPublisher.ifPresent(publisherRepository::deleteAndCheckEditions);
  }

  @Override
  @AddCreatedMetadata
  public Publisher update(Publisher publisher) {
    return publisherRepository.save(publisher);
  }
}
