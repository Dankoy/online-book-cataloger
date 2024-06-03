package ru.dankoy.library.core.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.dankoy.library.core.dto.work.WorkDTO;
import ru.dankoy.library.core.exceptions.Entity;
import ru.dankoy.library.core.exceptions.EntityNotFoundException;
import ru.dankoy.library.core.service.work.WorkService;

@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@RestController
public class WorkRestController {

  private final WorkService workService;

  @GetMapping(
      value = "/api/v1/work",
      produces = {"application/json"})
  public List<WorkDTO> getAll() {

    var books = workService.findAll();

    return books.stream().map(WorkDTO::toDTOWithoutCommentaries).collect(Collectors.toList());
  }

  @DeleteMapping(
      value = "/api/v1/work/{id}",
      consumes = {"application/json"},
      produces = {"application/json"})
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public void delete(@PathVariable String id) {

    workService.deleteById(id);
  }

  @GetMapping(
      value = "/api/v1/work/{id}",
      produces = {"application/json"})
  public WorkDTO getById(@PathVariable String id) {

    var book =
        workService.getById(id).orElseThrow(() -> new EntityNotFoundException(id, Entity.BOOK));

    return WorkDTO.toDTOWithoutCommentaries(book);
  }

  @PutMapping(
      value = "/api/v1/work/{id}",
      consumes = {"application/json"},
      produces = {"application/json"})
  public WorkDTO update(@PathVariable String id, @RequestBody WorkDTO workDTO) {

    var book = WorkDTO.toWork(workDTO);

    workService.getById(id).orElseThrow(() -> new EntityNotFoundException(id, Entity.BOOK));

    var updated = workService.update(book);

    return WorkDTO.toDTOWithoutCommentaries(updated);
  }

  @PostMapping(
      value = "/api/v1/work",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public WorkDTO create(@RequestBody WorkDTO workDTO) {

    var book = WorkDTO.toWork(workDTO);

    var created = workService.insert(book);

    return WorkDTO.toDTOWithoutCommentaries(created);
  }
}
