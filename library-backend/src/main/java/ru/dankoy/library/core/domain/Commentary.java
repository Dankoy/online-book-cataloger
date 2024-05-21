package ru.dankoy.library.core.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
// фиксит рекурсивный вызов equals и hashcode комментария и книги
@Getter
@Document("commentaries")
public class Commentary {

  @Id
  private String id;

  @Field("text")
  private String text;

  @DocumentReference(collection = "users", lookup = "{ '_id' : ?#{#target} }") // таким образом в бд хранится только id
  @Setter
  @Field("user")
  private User user; // id

  @DBRef(lazy = true)
  @JsonBackReference
  private Work work;

  @Setter
  @CreatedDate
  @Field("dt_created")
  private LocalDateTime dateCreated;

  @Setter
  @LastModifiedDate
  @Field("dt_modified")
  private LocalDateTime dateModified;

}
