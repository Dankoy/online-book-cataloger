package ru.dankoy.libraryauth.core.dto.commentary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.dankoy.libraryauth.core.domain.Commentary;
import ru.dankoy.libraryauth.core.dto.work.WorkCommentaryDTO;


@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryCreateDTO {

  private String id;

  private String text;

  @Setter
  private WorkCommentaryDTO work;

  public static Commentary toCommentary(CommentaryCreateDTO dto) {
    return new Commentary(dto.getId(),
        dto.getText(),
        null,
        WorkCommentaryDTO.fromDTO(dto.getWork()),
        null,
        null
    );
  }

  public static CommentaryCreateDTO toDTO(Commentary commentary) {
    return CommentaryCreateDTO.builder()
        .id(commentary.getId())
        .text(commentary.getText())
        .work(new WorkCommentaryDTO(commentary.getWork().getId()))
        .build();
  }

}
