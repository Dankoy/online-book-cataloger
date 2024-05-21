package ru.dankoy.libraryauth.core.exceptions;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Entity {
  BOOK("Book"),
  AUTHOR("Author"),
  GENRE("Genre"),
  COMMENTARY("Commentary"),
  PUBLISHER("Publisher"),
  SHELF("Shelf"),
  NOTE("Note"),
  EDITION("Edition");

  private final String name;

}
