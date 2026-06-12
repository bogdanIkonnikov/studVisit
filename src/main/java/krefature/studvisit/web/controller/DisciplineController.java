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
import krefature.studvisit.domain.service.DisciplineService;
import krefature.studvisit.web.dto.StandardResponse;
import krefature.studvisit.web.dto.ErrorResponse;
import krefature.studvisit.web.dto.discipline.DisciplineRequest;
import krefature.studvisit.web.dto.discipline.DisciplineResponse;
import krefature.studvisit.web.mapper.DisciplineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/disciplines")
@Tag(name = "Дисциплины", description = "API для управления дисциплинами")
public class DisciplineController {
    @Autowired
    private DisciplineService service;
    @Autowired
    private DisciplineMapper mapper;

    @GetMapping("")
    @Operation(summary = "Получить все дисциплины",
            description = "Возвращает список всех дисциплин в системе")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: оболочка StandardResponse с полем data = список DisciplineResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    public StandardResponse<List<DisciplineResponse>> getAll(){
        return new StandardResponse<>(true, service.getAll().stream().map(d -> mapper.toResponse(d)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить дисциплину по ID",
            description = "Возвращает информацию о дисциплине по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = DisciplineResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Дисциплина не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<DisciplineResponse> getById(
            @Parameter(description = "Идентификатор дисциплины")
            @PathVariable
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id){
        return new StandardResponse<>(true, mapper.toResponse(service.getById(id)));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать новую дисциплину",
            description = "Добавляет новую дисциплину в систему")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = созданная DisciplineResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<DisciplineResponse> add(@Valid @RequestBody DisciplineRequest request) {
        return new StandardResponse<>(true, mapper.toResponse(service.addDiscipline(mapper.toModel(request))));
    }

    @PutMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить дисциплину",
            description = "Обновляет информацию об существующей дисциплине")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = обновленная DisciplineResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Дисциплина не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<DisciplineResponse> edit(
            @Parameter(description = "Идентификатор дисциплины")
            @PathVariable
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id,
            @Valid @RequestBody DisciplineRequest request) {
        return new StandardResponse<>(true, mapper.toResponse(service.updateDiscipline(mapper.toModel(request, id))));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить дисциплину",
            description = "Удаляет дисциплину из системы по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data=null",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Дисциплина не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<Void> delete(
            @Parameter(description = "Идентификатор дисциплины")
            @PathVariable
            @Min(value = 1, message = "Идентификатор должен быть больше 1")
            @NotNull(message = "Идентификатор не должен быть пустым")
            Long id) {
        service.deleteDisciplineById(id);
        return new StandardResponse<>(true, null);
    }
}
