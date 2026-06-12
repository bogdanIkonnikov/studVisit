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
import krefature.studvisit.domain.model.GroupModel;
import krefature.studvisit.web.dto.StandardResponse;
import krefature.studvisit.web.dto.ErrorResponse;
import krefature.studvisit.web.dto.group.GroupRequest;
import krefature.studvisit.web.dto.group.GroupResponse;
import krefature.studvisit.domain.service.GroupService;
import krefature.studvisit.web.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Validated
@Tag(name = "Группы", description = "API для управления группами студентов")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupMapper mapper;

    @GetMapping("/getAll")
    @Operation(summary = "Получить все группы",
            description = "Возвращает список всех групп студентов в системе")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = список GroupResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    public StandardResponse<List<GroupResponse>> getStudentGroups() {
        return new StandardResponse<>(true, mapper.toResponses(groupService.getAllGroups()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить группу по ID",
            description = "Возвращает информацию о группе по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = GroupResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Группа не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<GroupResponse> getStudentGroupById(
            @Parameter(description = "Идентификатор группы")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id) {
        return new StandardResponse<>(true, mapper.toResponse(groupService.getGroupById(id)));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создать новую группу",
            description = "Добавляет новую группу студентов в систему")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = созданная GroupResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<GroupResponse> addStudentGroup(@Valid @RequestBody GroupRequest groupRequest) {
        return new StandardResponse<>(true, mapper.toResponse(groupService.addGroup(mapper.toModel(groupRequest))));
    }

    @PutMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновить группу",
            description = "Обновляет информацию об существующей группе")
    @ApiResponse(responseCode = "200", description = "Успешный ответ: StandardResponse с data = обновлённая GroupResponse",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)))
    @ApiResponse(responseCode = "404", description = "Группа не найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Неверные входные данные",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    public StandardResponse<GroupResponse> editStudentGroup(@Valid @RequestBody GroupRequest groupRequest,
                                          @Parameter(description = "Идентификатор группы")
                                          @NotNull @PathVariable Long id) {
        GroupModel groupModel = mapper.toModel(groupRequest, id);
        return new StandardResponse<>(true, mapper.toResponse(groupService.editGroup(groupModel)));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удалить группу",
            description = "Удаляет группу из системы по её идентификатору")
    @ApiResponse(responseCode = "200", description = "Группа успешно удалена")
    @ApiResponse(responseCode = "404", description = "Группа не найдена")
    public StandardResponse<Void> deleteStudentGroup(
            @Parameter(description = "Идентификатор группы")
            @NotNull(message = "id должен быть указан")
            @Min(value = 1, message = "id должен быть больше 0")
            @PathVariable Long id) {
        groupService.deleteGroup(id);
        return new StandardResponse<>(true, null);
    }
}
