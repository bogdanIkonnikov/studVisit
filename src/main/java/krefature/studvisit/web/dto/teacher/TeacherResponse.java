package krefature.studvisit.web.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherResponse {
    @Schema(description = "Идентификатор учителя", example = "1")
    private Long id;

    @Schema(description = "ФИО учителя", example = "Иванов Иван Иванович")
    private String teacherFIO;
}
