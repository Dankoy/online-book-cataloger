package ru.dankoy.libraryauth.core.dto.genre;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.dankoy.libraryauth.core.domain.Genre;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {

  private String name;


  public static Genre fromDTO(GenreDTO dto) {
    return new Genre(dto.getName());
  }

}
