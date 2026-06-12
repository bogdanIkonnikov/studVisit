package krefature.studvisit.web.dto.lessonVisit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessonVisitResponse {
    @Schema(description = "Название дисциплины", example = "MATH")
    private String name;

    @Schema(description = "Список ФИО студентов")
    private List<String> studentsFIO;
}
