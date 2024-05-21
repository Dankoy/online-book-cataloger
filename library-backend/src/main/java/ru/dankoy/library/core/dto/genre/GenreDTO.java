package ru.dankoy.library.core.dto.genre;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.dankoy.library.core.domain.Genre;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {

  private String name;


  public static Genre fromDTO(GenreDTO dto) {
    return new Genre(dto.getName());
  }

}
