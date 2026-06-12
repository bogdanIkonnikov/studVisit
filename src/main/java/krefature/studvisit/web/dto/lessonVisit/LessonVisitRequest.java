package krefature.studvisit.web.dto.lessonVisit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class LessonVisitRequest {
    @NotNull(message = "идентификатор урока должен быть указан")
    @Min(value = 1, message = "идентификатор урока должен быть больше 0")
    @Schema(description = "Идентификатор урока", example = "1")
    private Long lessonId;

    @NotEmpty(message = "список студентов должен быть указан и не пустой")
    @Schema(description = "Список идентификаторов студентов")
    private List<Long> studentIds;
}


