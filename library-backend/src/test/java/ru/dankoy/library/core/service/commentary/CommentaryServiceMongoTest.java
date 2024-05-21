package ru.dankoy.library.core.service.commentary;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.dankoy.library.core.domain.Commentary;
import ru.dankoy.library.core.domain.Work;
import ru.dankoy.library.core.exceptions.EntityNotFoundException;
import ru.dankoy.library.core.repository.commentary.CommentaryRepository;
import ru.dankoy.library.core.service.author.AuthorServiceMongo;
import ru.dankoy.library.core.service.genre.GenreServiceMongo;
import ru.dankoy.library.core.service.work.WorkService;
import ru.dankoy.library.core.service.work.WorkServiceMongo;


@Transactional(propagation = Propagation.NEVER)
@DisplayName("Tests for CommentaryServiceMongo ")
@DataMongoTest
@Import({CommentaryServiceMongo.class, WorkServiceMongo.class, GenreServiceMongo.class,
    AuthorServiceMongo.class})
class CommentaryServiceMongoTest {


//  @MockBean
//  private WorkService workService;
//
//  @MockBean
//  private CommentaryRepository commentaryRepository;
//
//  @Autowired
//  private CommentaryService commentaryService;
//
//
//  @DisplayName("should correctly return commentary by id")
//  @Test
//  void shouldCorrectlyReturnCommentaryById() {
//
//    var id = "1L";
//
//    var book = new Work(id, "name", "descr", new HashSet<>(), new HashSet<>(), null, null,
//        null);
//    var found = new Commentary(id, "com", null, book, null, null);
//    given(commentaryRepository.findById(id)).willReturn(Optional.of(found));
//
//    var actual = commentaryService.getById(id);
//
//    assertThat(actual).isPresent().get().isEqualTo(found);
//    Mockito.verify(commentaryRepository, times(1)).findById(id);
//
//  }
//
//
//  @DisplayName("should correctly return empty commentary by id")
//  @Test
//  void shouldReturnEmptyCommentaryById() {
//
//    var id = "1L";
//
//    given(commentaryRepository.findById(id)).willReturn(Optional.empty());
//
//    var actual = commentaryService.getById(id);
//
//    assertThat(actual).isEmpty();
//    Mockito.verify(commentaryRepository, times(1)).findById(id);
//
//  }
//
//
//  @DisplayName("should correctly return all commentaries by book id")
//  @Test
//  void shouldReturnAllCommentariesByBookId() {
//
//    var id = "1L";
//
//    given(commentaryRepository.findAllByWorkId(id)).willReturn(
//        new ArrayList<>(makeCorrectCommentaryList()));
//
//    var actual = commentaryService.getAllByBookId(id);
//
//    assertThat(actual).isEqualTo(new ArrayList<>(makeCorrectCommentaryList()));
//    Mockito.verify(commentaryRepository, times(1)).findAllByWorkId(id);
//
//  }
//
//
//  @DisplayName("should correctly deleteByBookId commentary by id")
//  @Test
//  void shouldCorrectlyDeleteCommentaryById() {
//
//    var id = "1L";
//
//    var book = new Work(id, "name", "descr", new HashSet<>(), new HashSet<>(), null, null,
//        null);
//    var commentary = new Commentary(id, "com", null, book, null, null);
//    given(workService.getById(id)).willReturn(Optional.of(book));
//    given(commentaryRepository.findById(id)).willReturn(Optional.of(commentary));
//
//    commentaryService.deleteById(id);
//
//    Mockito.verify(commentaryRepository, times(1)).findById(id);
//    Mockito.verify(commentaryRepository, times(1)).delete(commentary);
//
//  }
//
//
//  @DisplayName("should throw exception when deleteByBookId commentary by id")
//  @Test
//  void shouldThrowExceptionWhenDeleteCommentaryById() {
//
//    var id = "1L";
//
//    var book = new Work(id, "name", "descr", new HashSet<>(), new HashSet<>(), null, null,
//        null);
//    given(workService.getById(id)).willReturn(Optional.of(book));
//    given(commentaryRepository.findById(id)).willReturn(Optional.empty());
//
//    assertThatThrownBy(() -> commentaryService.deleteById(id))
//        .isInstanceOf(EntityNotFoundException.class);
//
//    Mockito.verify(commentaryRepository, times(1)).findById(id);
//    Mockito.verify(commentaryRepository, times(0)).delete(any());
//
//  }
//
//
//  @DisplayName("should correctly update commentary")
//  @Test
//  void shouldCorrectlyUpdateCommentary() {
//
//    var id = "1L";
//
//    var book = new Work(id, "name", "descr", new HashSet<>(), new HashSet<>(), null, null,
//        null);
//    var commentary = new Commentary(id, "com", null, book, null, null);
//
//    commentaryService.insertOrUpdate(commentary);
//
//    Mockito.verify(commentaryRepository, times(1)).save(commentary);
//
//
//  }
//
//
//  private Set<Commentary> makeCorrectCommentaryList() {
//
//    var book = new Work("1L", "name", "descr", new HashSet<>(), new HashSet<>(), null, null,
//        null);
//    return Set.of(new Commentary("1L", "com1", null, book, null, null),
//        new Commentary("2L", "com2", null, book, null, null),
//        new Commentary("3L", "com3", null, book, null, null));
//
//  }

}
