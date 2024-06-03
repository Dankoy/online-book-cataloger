package ru.dankoy.library.core.healthcheck.mongock.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dankoy.library.core.healthcheck.mongock.domain.MongockChangeLog;

@Repository
public interface MongockChangeLogsRepository extends MongoRepository<MongockChangeLog, String> {}
