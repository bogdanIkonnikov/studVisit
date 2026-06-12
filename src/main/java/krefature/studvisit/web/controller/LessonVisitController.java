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
import krefature.studvisit.domain.model.LessonVisitModel;
import krefature.studvisit.domain.service.LessonVisitService;
import krefature.studvisit.web.dto.StandardResponse;
import krefature.studvisit.web.dto.ErrorResponse;
import krefature.studvisit.web.dto.lessonVisit.LessonVisitRequest;
import krefature.studvisit.web.dto.lessonVisit.LessonVisitResponse;
import krefature.studvisit.web.mapper.LessonVisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson-visits")
@Validated
@Tag(name = "Посещения уроков", description = "API для управления посещениями студентов на уроках")
public class LessonVisitController {
    @Autowired
    private LessonVisitService lessonVisitService;
    @Autowired
    private LessonVisitMapper mapper;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Создать запись о посещении",
            description = "Добавляет студентов к списку присутствующих на уроке")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = LessonVisitResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Урок или студент не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<LessonVisitResponse> addLessonVisit(@Valid @RequestBody LessonVisitRequest request) {
        return new StandardResponse<>(true, mapper.toResponse(lessonVisitService.addLessonVisit(mapper.toModel(request))));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить запись о посещении по ID",
            description = "Возвращает информацию о посещении урока по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = LessonVisitResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Запись о посещении не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<LessonVisitResponse> getLessonVisitById(
            @Parameter(description = "Идентификатор записи о посещении")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id) {
        return new StandardResponse<>(true, mapper.toResponse(lessonVisitService.getLessonVisitById(id)));
    }

    @PutMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Обновить запись о посещении",
            description = "Обновляет информацию об существующей записи о посещении")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = LessonVisitResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Запись о посещении не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<LessonVisitResponse> editLessonVisit(
            @Parameter(description = "Идентификатор записи о посещении")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id,
            @Valid @RequestBody LessonVisitRequest request) {
        LessonVisitModel model = mapper.toModel(request);
        model.setId(id);
        return new StandardResponse<>(true, mapper.toResponse(lessonVisitService.updateLessonVisit(model)));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @Operation(summary = "Удалить запись о посещении",
            description = "Удаляет запись о посещении по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Запись о посещении успешно удалена")
    @ApiResponse(responseCode = "404", description = "Запись о посещении не найдена")
    public StandardResponse<Void> deleteLessonVisit(
            @Parameter(description = "Идентификатор записи о посещении")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id) {
        lessonVisitService.deleteLessonVisitById(id);
        return new StandardResponse<>(true, null);
    }
}


