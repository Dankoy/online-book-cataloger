package ru.dankoy.library.core.repository.publisher;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.dankoy.library.core.domain.Publisher;

//@RepositoryRestResource(path = "publisher")
public interface PublisherRepository extends MongoRepository<Publisher, String>,
    PublisherRepositoryCustom {

}
