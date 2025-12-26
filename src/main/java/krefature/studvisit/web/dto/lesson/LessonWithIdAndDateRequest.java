package krefature.studvisit.web.dto.lesson;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonWithIdAndDateRequest {
    @NotNull(message = "id не может быть пустым")
    @Min(value = 1, message = "id должен быть больше 0")
    private Long id;
    @NotBlank(message = "дата должна быть заполнена")
    private String dateAfter;
    @NotBlank(message = "дата должна быть заполнена")
    private String dateBefore;
}
