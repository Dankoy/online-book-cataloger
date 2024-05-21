package ru.dankoy.libraryauth;

import com.github.cloudyrock.spring.v5.EnableMongock;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableZuulProxy
@EnableEurekaClient
@EnableMongock
@EnableMongoRepositories(basePackages = {"ru.dankoy.libraryauth.core.repository"})
@EnableEncryptableProperties
@SpringBootApplication
public class LibraryAuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(LibraryAuthApplication.class, args);
  }

}
