package ru.dankoy.libraryauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {
    "ru.dankoy.libraryauth.core.healthcheck.mongock.repository"})
public class MongoHealthRepoConfig {

}
