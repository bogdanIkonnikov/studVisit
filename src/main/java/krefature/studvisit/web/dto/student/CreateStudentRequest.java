package krefature.studvisit.web.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.enums.Status;
import lombok.Data;

@Data
public class CreateStudentRequest {
    @NotBlank(message = "фамилия должна быть заполнена")
    @Schema(description = "Фамилия студента", example = "Сидоров")
    private String firstName;

    @NotBlank(message = "имя должно быть заполнено")
    @Schema(description = "Имя студента", example = "Петр")
    private String lastName;

    @NotBlank(message = "отчество должно быть заполнено")
    @Schema(description = "Отчество студента", example = "Петрович")
    private String middleName;

    @NotNull(message = "группа должна быть указана")
    @Schema(description = "Идентификатор группы", example = "1")
    private Long groupId;

    @NotNull(message = "статус должен быть указан")
    @Schema(description = "Статус студента", example = "ACTIVE")
    private Status status;
}
