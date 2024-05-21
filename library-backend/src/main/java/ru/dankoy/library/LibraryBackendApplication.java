package ru.dankoy.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableEurekaClient
@EnableMongock
@EnableMongoRepositories(basePackages = {"ru.dankoy.library.core.repository"})
@EnableEncryptableProperties
@SpringBootApplication
public class LibraryBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(LibraryBackendApplication.class, args);
  }

}
