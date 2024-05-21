package ru.dankoy.library;

import com.github.cloudyrock.spring.v5.EnableMongock;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@EnableDiscoveryClient
@EnableMongock
@EnableMongoRepositories(basePackages = {"ru.dankoy.library.core.repository"})
@EnableEncryptableProperties
@SpringBootApplication
public class LibraryBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(LibraryBackendApplication.class, args);
  }

}
