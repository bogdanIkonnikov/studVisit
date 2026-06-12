package krefature.studvisit.web.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessonWithVisitResponse {
    @Schema(description = "Идентификатор урока", example = "1")
    private Long id;

    @Schema(description = "Дата урока", example = "2026-05-23")
    private String date;

    @Schema(description = "Время урока (порядковый номер)", example = "3")
    private int time;

    @Schema(description = "Идентификатор учителя", example = "1")
    private Long teacherId;

    @Schema(description = "ФИО учителя", example = "Иванов Иван Иванович")
    private String teacherFIO;

    @Schema(description = "Идентификатор группы", example = "1")
    private Long groupId;

    @Schema(description = "Идентификатор дисциплины", example = "1")
    private Long disciplineId;

    @Schema(description = "Список идентификаторов студентов, присутствующих на уроке")
    private List<Long> studentIds;
}