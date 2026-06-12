package krefature.studvisit.web.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonWithIdAndDateRequest {
    @NotNull(message = "id не может быть пустым")
    @Min(value = 1, message = "id должен быть больше 0")
    @Schema(description = "Идентификатор учителя или группы", example = "1")
    private Long id;

    @NotBlank(message = "дата должна быть заполнена")
    @Schema(description = "Начальная дата в формате YYYY-MM-DD", example = "2026-05-01")
    private String dateAfter;

    @NotBlank(message = "дата должна быть заполнена")
    @Schema(description = "Конечная дата в формате YYYY-MM-DD", example = "2026-05-31")
    private String dateBefore;
}
