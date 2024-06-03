package ru.dankoy.spring_authorization_server.core.exceptions;

public class LibraryRootException extends RuntimeException {

  public LibraryRootException(String message, Exception e) {
    super(message, e);
  }

  public LibraryRootException(String message) {
    super(message);
  }
}
