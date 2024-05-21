package ru.dankoy.library.core.exceptions;

public class EntityNotFoundException extends Hw5RootException {

  private static final String MESSAGE = "Entity %s has not been found with id - %s";

  public EntityNotFoundException(String id, Entity entity) {
    super(String.format(MESSAGE, entity.getName(), id));
  }

}
