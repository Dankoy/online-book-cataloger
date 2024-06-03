package ru.dankoy.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"ru.dankoy.library.core.healthcheck.mongock.repository"})
public class MongoHealthRepoConfig {}
