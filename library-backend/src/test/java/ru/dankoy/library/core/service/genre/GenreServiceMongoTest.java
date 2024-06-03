package ru.dankoy.library.core.service.genre;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.NEVER)
@DisplayName("Test GenreServiceMongo ")
@DataMongoTest
@Import({GenreServiceMongo.class})
class GenreServiceMongoTest {

  //  @MockBean
  //  private WorkService workService;
  //
  //  @Autowired
  //  private GenreServiceMongo genreServiceMongo;
  //
  //
  //  @DisplayName("should return all genres")
  //  @Test
  //  void shouldGetAllGenresTest() {
  //
  //    given(workService.findAll()).willReturn(makeCorrectAllBooksList());
  //
  //    var genres = genreServiceMongo.getAllGenres();
  //
  //    assertThat(genres).isEqualTo(makeCorrectAllGenresList());
  //    Mockito.verify(workService, times(1)).findAll();
  //  }
  //
  //
  //  @DisplayName("should correctly update genres")
  //  @Test
  //  void shouldUpdateGenre() {
  //
  //    var oldGenre = new Genre("genre1");
  //    var newGenre = new Genre("new");
  //    var booksReturnFromService = makeCorrectAllBooksList();
  //    var expectedBooks = makeCorrectAllBooksList();
  //
  //    given(workService.findAllByGenreName(oldGenre)).willReturn(booksReturnFromService);
  //
  //    booksAfterUpdateGenres(expectedBooks, oldGenre, newGenre);
  //
  //    genreServiceMongo.update(oldGenre, newGenre);
  //
  //    Mockito.verify(workService, times(1))
  //        .updateMultiple(expectedBooks);
  //    Mockito.verify(workService, times(1))
  //        .findAllByGenreName(oldGenre);
  //    assertThat(expectedBooks).containsExactlyInAnyOrderElementsOf(booksReturnFromService);
  //
  //  }
  //
  //  @DisplayName("should correctly deleteByBookId genres from books")
  //  @Test
  //  void shouldDeleteGenre() {
  //
  //    var toDelete = new Genre("genre1");
  //    var booksReturnFromService = makeCorrectAllBooksList();
  //    var expectedBooks = makeCorrectAllBooksList();
  //
  //    given(workService.findAllByGenreName(toDelete)).willReturn(booksReturnFromService);
  //
  //    booksAfterDeleteGenres(expectedBooks, toDelete);
  //
  //    genreServiceMongo.delete(toDelete);
  //
  //    Mockito.verify(workService, times(1)).updateMultiple(expectedBooks);
  //    Mockito.verify(workService, times(1)).findAllByGenreName(toDelete);
  //    assertThat(expectedBooks).containsExactlyInAnyOrderElementsOf(booksReturnFromService);
  //
  //  }
  //
  //  private void booksAfterUpdateGenres(List<Work> works, Genre oldGenre, Genre newGenre) {
  //
  //    works.forEach(b -> {
  //      b.getGenres().remove(oldGenre);
  //      b.getGenres().add(newGenre);
  //    });
  //
  //  }
  //
  //  private void booksAfterDeleteGenres(List<Work> works, Genre toDelete) {
  //
  //    works.forEach(b -> b.getGenres().remove(toDelete));
  //
  //  }
  //
  //
  //  private Set<Genre> makeCorrectAllGenresList() {
  //    return Set.of(
  //        new Genre("genre1"),
  //        new Genre("genre2"),
  //        new Genre("genre3")
  //    );
  //  }
  //
  //
  //  private List<Work> makeCorrectAllBooksList() {
  //
  //    Set<Genre> genreBook1 = new HashSet<>();
  //    genreBook1.add(new Genre("genre1"));
  //    genreBook1.add(new Genre("genre2"));
  //
  //    Set<Genre> genreBook2 = new HashSet<>();
  //    genreBook2.add(new Genre("genre2"));
  //    genreBook2.add(new Genre("genre3"));
  //
  //    Set<Genre> genreBook3 = new HashSet<>();
  //    genreBook3.add(new Genre("genre1"));
  //    genreBook3.add(new Genre("genre3"));
  //
  //    var book1 = new Work("1L", "book1", "descr",
  //        Set.of(new Author("1L", "author1",null, null, null, null), new Author("2L",
  // "author2",null, null, null, null)),
  //        genreBook1,null, null, null);
  //
  //    var book2 = new Work("2L", "book2", "descr",
  //        Set.of(new Author("2L", "author2",null, null, null, null), new Author("3L",
  // "author3",null, null, null, null)),
  //        genreBook2,null, null, null);
  //
  //    var book3 = new Work("3L", "book3", "descr",
  //        Set.of(new Author("1L", "author1",null, null, null, null), new Author("3L",
  // "author3",null, null, null, null)),
  //        genreBook3,null, null, null);
  //
  //    return List.of(
  //        book1,
  //        book2,
  //        book3
  //    );
  //  }

}
