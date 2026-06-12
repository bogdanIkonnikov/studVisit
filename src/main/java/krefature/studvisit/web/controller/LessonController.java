package krefature.studvisit.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.domain.service.LessonService;
import krefature.studvisit.web.dto.StandardResponse;
import krefature.studvisit.web.dto.ErrorResponse;
import krefature.studvisit.web.dto.PageResponse;
import krefature.studvisit.web.dto.lesson.LessonAddRequest;
import krefature.studvisit.web.dto.lesson.LessonResponse;
import krefature.studvisit.web.dto.lesson.LessonWithIdAndDateRequest;
import krefature.studvisit.web.dto.lesson.LessonWithVisitResponse;
import krefature.studvisit.web.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
@Tag(name = "Уроки", description = "API для управления уроками")
public class LessonController {
    @Autowired
    private LessonService service;
    @Autowired
    private LessonMapper mapper;

    @PostMapping("/all/by-teacher")
    @Operation(summary = "Получить уроки по учителю и дате",
            description = "Возвращает список уроков для учителя в указанном диапазоне дат")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = список LessonResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<?> getAllByTeacher(
            @RequestBody @Valid LessonWithIdAndDateRequest request,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page != null || size != null) {
            int resolvedPage = page != null ? page : 0;
            int resolvedSize = size != null ? size : 10;
            Page<LessonResponse> data = service.getPageByTeacherIdAndDate(mapper.toModel(request), PageRequest.of(resolvedPage, resolvedSize))
                    .map(mapper::toResponse);
            return new StandardResponse<>(true, new PageResponse<>(
                    data.getContent(),
                    data.getNumber(),
                    data.getSize(),
                    data.getTotalElements(),
                    data.getTotalPages()
            ));
        }
        List<LessonResponse> data = service.getAllByTeacherIdAndDate(mapper.toModel(request)).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new StandardResponse<>(true, data);
    }

    @PostMapping("/all/by-group")
    @Operation(summary = "Получить уроки по группе и дате",
            description = "Возвращает список уроков для группы в указанном диапазоне дат")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = список LessonResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<?> getAllByGroup(
            @RequestBody @Valid LessonWithIdAndDateRequest request,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page != null || size != null) {
            int resolvedPage = page != null ? page : 0;
            int resolvedSize = size != null ? size : 10;
            Page<LessonResponse> data = service.getPageByGroupIdAndDate(mapper.toModel(request), PageRequest.of(resolvedPage, resolvedSize))
                    .map(mapper::toResponse);
            return new StandardResponse<>(true, new PageResponse<>(
                    data.getContent(),
                    data.getNumber(),
                    data.getSize(),
                    data.getTotalElements(),
                    data.getTotalPages()
            ));
        }
        List<LessonResponse> data = service.getAllByGroupIdAndDate(mapper.toModel(request)).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new StandardResponse<>(true, data);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить урок с посещениями",
            description = "Возвращает информацию об уроке вместе со списком присутствующих студентов")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = LessonWithVisitResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Урок не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<LessonWithVisitResponse> getById(
            @Parameter(description = "Идентификатор урока")
            @PathVariable
            @NotNull(message = "id не может быть пустым")
            @Min(value = 1, message = "id должен быть больше 0")
            Long id) {
        LessonWithVisitResponse data = mapper.toVisitResponse(service.findById(id));
        return new StandardResponse<>(true, data);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Создать новый урок",
            description = "Создаёт новый урок в системе")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = созданный LessonResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<LessonResponse> create(@RequestBody @Valid LessonAddRequest request) {
        LessonResponse data = mapper.toResponse(service.create(mapper.toModel(request)));
        return new StandardResponse<>(true, data);
    }

    @PutMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Обновить урок",
            description = "Обновляет информацию об существующем уроке")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = обновлённый LessonResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Урок не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<LessonResponse> update(@RequestBody @Valid LessonAddRequest request,
                                 @Parameter(description = "Идентификатор урока")
                                 @PathVariable @NotNull(message = "id не может быть пустым")
                                 @Min(value = 1, message = "id должен быть больше 0")
                                 Long id) {
        LessonResponse data = mapper.toResponse(service.update(mapper.toModel(request, id)));
        return new StandardResponse<>(true, data);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Удалить урок",
            description = "Удаляет урок по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data=null",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Урок не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<Void> deleteById(
            @Parameter(description = "Идентификатор урока")
            @PathVariable
            @NotNull(message = "id не может быть пустым")
            @Min(value = 1, message = "id должен быть больше 0")
            Long id) {
        service.deleteById(id);
        return new StandardResponse<>(true, null);
    }

    @DeleteMapping("/delete/teacher/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить все уроки учителя",
            description = "Удаляет все уроки учителя по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data=null",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Учитель не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<Void> deleteT(@Parameter(description = "Идентификатор учителя") @PathVariable Long id) {
        service.deleteAllByTeacherId(id);
        return new StandardResponse<>(true, null);
    }

    @DeleteMapping("/delete/group/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить все уроки группы",
            description = "Удаляет все уроки группы по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data=null",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Группа не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<Void> deleteG(@Parameter(description = "Идентификатор группы") @PathVariable Long id) {
        service.deleteAllByGroupId(id);
        return new StandardResponse<>(true, null);
    }
}
