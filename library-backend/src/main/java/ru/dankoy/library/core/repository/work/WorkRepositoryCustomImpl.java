package ru.dankoy.library.core.repository.work;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.dankoy.library.core.domain.Author;
import ru.dankoy.library.core.domain.Edition;
import ru.dankoy.library.core.domain.Genre;
import ru.dankoy.library.core.domain.Work;
import ru.dankoy.library.core.exceptions.Entity;
import ru.dankoy.library.core.exceptions.EntityNotFoundException;
import ru.dankoy.library.core.exceptions.Hw5RootException;
import ru.dankoy.library.core.repository.commentary.CommentaryRepository;

@RequiredArgsConstructor
public class WorkRepositoryCustomImpl implements WorkRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  private final CommentaryRepository commentaryRepository;

  @Override
  public List<Genre> getAllGenresByBookId(String bookId) {
    var aggregation =
        newAggregation(
            match(Criteria.where("id").is(bookId)),
            unwind("genres"),
            project().andExclude("_id").and("genres.name").as("name"));

    return mongoTemplate.aggregate(aggregation, Work.class, Genre.class).getMappedResults();
  }

  @Override
  public Work saveAndCheckAuthors(Work work) {

    Set<Author> authors = work.getAuthors();

    // проверяем, что авторы присутствуют в коллекции авторов
    authors.forEach(
        author -> {
          List<Author> found =
              mongoTemplate.find(
                  new Query().addCriteria(Criteria.where("_id").is(author.getId())), Author.class);

          if (found.isEmpty()) {
            throw new EntityNotFoundException(author.getId(), Entity.AUTHOR);
          }
        });

    return mongoTemplate.save(work, "works");
  }

  @Override
  public void deleteByWorkId(String workId) {

    var query = Query.query(Criteria.where("_id").is(workId));

    var foundWork = Optional.ofNullable(mongoTemplate.findOne(query, Work.class));

    foundWork.ifPresent(
        w -> {

          // Если есть издания, то удалять запрещено
          if (!w.getEditions().isEmpty()) {

            var editionsIds =
                w.getEditions().stream().map(Edition::getId).collect(Collectors.toSet());

            throw new Hw5RootException(
                String.format("Can't delete work, because editions require it. '%s'", editionsIds));
          }

          // todo: сделать через mongoTemplate. избавиться от зависимости репы комментариев
          commentaryRepository.deleteCommentariesByWorkId(workId);
          mongoTemplate.remove(query, Work.class);
        });
  }
}
