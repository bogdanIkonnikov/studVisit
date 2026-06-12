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
import krefature.studvisit.domain.model.StudentModel;
import krefature.studvisit.web.dto.StandardResponse;
import krefature.studvisit.web.dto.ErrorResponse;
import krefature.studvisit.web.dto.student.CreateStudentRequest;
import krefature.studvisit.web.dto.student.StudentResponse;
import krefature.studvisit.web.dto.student.EditStudentRequest;
import krefature.studvisit.domain.service.StudentService;
import krefature.studvisit.web.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Validated
@Tag(name = "Студенты", description = "API для управления студентами")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper mapper;

    @GetMapping("/getStudentById/{id}")
    @Operation(summary = "Получить студента по ID",
            description = "Возвращает информацию о студенте по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = StudentResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Студент не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<StudentResponse> getStudentById(
            @Parameter(description = "Идентификатор студента")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id) {
        return new StandardResponse<>(true, mapper.toResponse(studentService.getStudentById(id)));
    }

    @GetMapping("/getStudentByGroupId/{id}")
    @Operation(summary = "Получить студентов по ID группы",
            description = "Возвращает список всех студентов в указанной группе")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = список StudentResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Группа не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<List<StudentResponse>> getStudentsByGroupId(
            @Parameter(description = "Идентификатор группы")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id) {
        return new StandardResponse<>(true, mapper.toResponses(studentService.getStudentsByGroupId(id)));
    }

    @PostMapping("/addStudent")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать нового студента",
            description = "Добавляет нового студента в систему")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = созданный StudentResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<StudentResponse> addStudent(@Valid @RequestBody CreateStudentRequest request) {
        return new StandardResponse<>(true, mapper.toResponse(studentService.addStudent(mapper.toModel(request))));
    }

    @PutMapping("/editStudent")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить студента",
            description = "Обновляет информацию об существующем студенте")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = обновлённый StudentResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Студент не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<StudentResponse> editStudent(@Valid @RequestBody EditStudentRequest request) {
        StudentModel model = mapper.toModel(request);
        return new StandardResponse<>(true, mapper.toResponse(studentService.updateStudent(model)));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить студента",
            description = "Удаляет студента из системы по его идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data=null",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Студент не найден",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<Void> deleteStudent(
            @Parameter(description = "Идентификатор студента")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id) {
        studentService.deleteStudent(id);
        return new StandardResponse<>(true, null);
    }

}
