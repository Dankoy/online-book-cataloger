package ru.dankoy.spring_authorization_server.core.exceptions;

public class RegistrationException extends LibraryRootException {

  public RegistrationException(String message, Exception e) {
    super(message, e);
  }

  public RegistrationException(String message) {
    super(message);
  }
}
