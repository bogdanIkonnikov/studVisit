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
import krefature.studvisit.domain.model.TeacherModel;
import krefature.studvisit.web.dto.StandardResponse;
import krefature.studvisit.web.dto.ErrorResponse;
import krefature.studvisit.web.dto.PageResponse;
import krefature.studvisit.web.dto.teacher.TeacherRequest;
import krefature.studvisit.web.dto.teacher.TeacherResponse;
import krefature.studvisit.domain.service.TeacherService;
import krefature.studvisit.web.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Validated
@Tag(name = "Учителя", description = "API для управления учителями")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper mapper;

    @GetMapping("")
    @Operation(summary = "Получить всех учителей",
            description = "Возвращает список всех учителей в системе")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = список TeacherResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    public StandardResponse<?> getAllTeachers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        if (page != null || size != null) {
            int resolvedPage = page != null ? page : 0;
            int resolvedSize = size != null ? size : 10;
            Page<TeacherResponse> data = teacherService.getTeachersPage(PageRequest.of(resolvedPage, resolvedSize))
                    .map(mapper::toResponse);
            return new StandardResponse<>(true, new PageResponse<>(
                    data.getContent(),
                    data.getNumber(),
                    data.getSize(),
                    data.getTotalElements(),
                    data.getTotalPages()
            ));
        }
        return new StandardResponse<>(true, mapper.toResponses(teacherService.getAllTeachers()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить учителя по ID",
            description = "Возвращает информацию об учителе по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = TeacherResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Учитель не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<TeacherResponse> getTeacherById(
            @Parameter(description = "Идентификатор учителя")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable("id") Long teacherId) {
        return new StandardResponse<>(true, mapper.toResponse(teacherService.getTeacherById(teacherId)));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать нового учителя",
            description = "Добавляет нового учителя в систему")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = созданный TeacherResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<TeacherResponse> addTeacher(@Valid @RequestBody TeacherRequest teacherRequest) {
        TeacherModel model = mapper.toModel(teacherRequest);
        return new StandardResponse<>(true, mapper.toResponse(teacherService.addTeacher(model)));
    }

    @PutMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить учителя",
            description = "Обновляет информацию об существующем учителе")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = обновлённый TeacherResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Учитель не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<TeacherResponse> editTeacher(@Valid @RequestBody TeacherRequest teacherRequest,
                                       @Parameter(description = "Идентификатор учителя")
                                       @PathVariable("id") Long id) {
        TeacherModel model = mapper.toModel(teacherRequest, id);
        return new StandardResponse<>(true, mapper.toResponse(teacherService.editTeacher(model)));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить учителя",
            description = "Удаляет учителя из системы по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data=null",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Учитель не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<Void> deleteTeacher(
            @Parameter(description = "Идентификатор учителя")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
        return new StandardResponse<>(true, null);
    }
}
