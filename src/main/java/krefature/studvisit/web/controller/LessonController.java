package krefature.studvisit.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.service.service.LessonService;
import krefature.studvisit.web.dto.lesson.LessonAddRequest;
import krefature.studvisit.web.dto.lesson.LessonResponse;
import krefature.studvisit.web.dto.lesson.LessonWithIdAndDateRequest;
import krefature.studvisit.web.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private LessonService service;
    @Autowired
    private LessonMapper mapper;

    @PostMapping("/all/by-teacher")
    public List<LessonResponse> getAllByTeacher(@RequestBody @Valid LessonWithIdAndDateRequest request) {
        return service.getAllByTeacherIdAndDate(mapper.toModel(request)).stream()
                .map(l -> mapper.toResponse(l)).collect(Collectors.toList());
    }
    @PostMapping("/all/by-group")
    public List<LessonResponse> getAllByGroup(@RequestBody @Valid LessonWithIdAndDateRequest request) {
        return service.getAllByGroupIdAndDate(mapper.toModel(request)).stream()
                .map(l -> mapper.toResponse(l)).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public LessonResponse getById(@PathVariable
                                      @NotNull(message = "id не может быть пустым")
                                      @Min(value = 1, message = "id должен быть больше 0")
                                      Long id) {
        return mapper.toResponse(service.findById(id));
    }
    @PostMapping("/add")
    public LessonResponse create(@RequestBody @Valid LessonAddRequest request) {
        return mapper.toResponse(service.create(mapper.toModel(request)));
    }
    @PutMapping("/{id}/edit")
    public LessonResponse update(@RequestBody @Valid LessonAddRequest request,
                                 @PathVariable @NotNull(message = "id не может быть пустым")
                                 @Min(value = 1, message = "id должен быть больше 0")
                                 Long id) {
        return mapper.toResponse(service.update(mapper.toModel(request, id)));
    }
    @DeleteMapping("/delete/teacher/{id}")
    public void deleteT(@PathVariable Long id) {
        service.deleteAllByTeacherId(id);
    }
    @DeleteMapping("/delete/group/{id}")
    public void deleteG(@PathVariable Long id) {
        service.deleteAllByGroupId(id);
    }
}
