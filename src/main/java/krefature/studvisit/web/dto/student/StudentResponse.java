package krefature.studvisit.web.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import krefature.studvisit.common.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentResponse {
    @Schema(description = "Идентификатор студента", example = "1")
    private Long id;

    @Schema(description = "Фамилия студента", example = "Сидоров")
    private String firstName;

    @Schema(description = "Имя студента", example = "Петр")
    private String middleName;

    @Schema(description = "Отчество студента", example = "Петрович")
    private String lastName;

    @Schema(description = "Идентификатор группы", example = "1")
    private Long groupId;

    @Schema(description = "Название группы", example = "М-101")
    private String groupName;

    @Schema(description = "Статус студента", example = "ACTIVE")
    private Status status;
}
