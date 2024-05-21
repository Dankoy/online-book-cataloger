package ru.dankoy.libraryauth.core.dto.work;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.dankoy.libraryauth.core.domain.Work;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkCommentaryDTO {

  private String id;

  public static Work fromDTO(WorkCommentaryDTO dto) {
    return new Work(dto.id);
  }

}
