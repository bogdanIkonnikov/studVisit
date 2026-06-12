package krefature.studvisit.web.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LessonAddRequest {
    @NotNull(message = "идентификатор учителя должен быть указан")
    @Min(value = 1, message = "идентификатор учителя должен быть больше 0")
    @Schema(description = "Идентификатор учителя", example = "1")
    private Long teacherId;

    @NotBlank(message = "дата должна быть заполнена")
    @Schema(description = "Дата урока в формате YYYY-MM-DD", example = "2026-05-23")
    private String Date;

    @NotNull(message = "идентификатор группы должен быть указан")
    @Min(value = 1, message = "идентификатор группы должен быть больше 0")
    @Schema(description = "Идентификатор группы", example = "1")
    private Long groupId;

    @NotNull(message = "идентификатор дисциплины должен быть указан")
    @Min(value = 1, message = "идентификатор дисциплины должен быть больше 0")
    @Schema(description = "Идентификатор дисциплины", example = "1")
    private Long disciplineId;

    @Min(value = 0, message = "время должно быть больше или равно 0")
    @Max(value = 23, message = "время должно быть меньше или равно 23")
    @Schema(description = "Время урока (час)", example = "10")
    private int time;
}
