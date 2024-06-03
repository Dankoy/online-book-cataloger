package ru.dankoy.library.core.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dankoy.library.core.domain.Genre;
import ru.dankoy.library.core.dto.genre.GenreDTO;
import ru.dankoy.library.core.service.genre.GenreService;

@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@RestController
public class GenreRestController {

  private final GenreService genreService;

  @GetMapping("/api/v1/genre")
  public Set<Genre> getAll() {

    return genreService.getAllGenres();
  }

  @DeleteMapping("/api/v1/genre")
  public void delete(@RequestBody GenreDTO dto) {

    var genre = GenreDTO.fromDTO(dto);
    genreService.delete(genre);
  }

  @PutMapping("/api/v1/genre")
  public void update(@RequestBody GenreDTO newGenreDto, @RequestParam String oldGenre) {

    var old = new Genre(oldGenre);
    var newGenre = GenreDTO.fromDTO(newGenreDto);

    genreService.update(old, newGenre);
  }
}
