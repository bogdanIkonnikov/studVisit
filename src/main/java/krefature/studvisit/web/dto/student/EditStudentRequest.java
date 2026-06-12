package krefature.studvisit.web.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.enums.Status;
import lombok.Data;

@Data
public class EditStudentRequest {
    @NotNull(message = "идентификатор студента должен быть указан")
    @Min(value = 1, message = "идентификатор должен быть больше 0")
    @Schema(description = "Идентификатор студента", example = "1")
    Long Id;

    @NotBlank(message = "фамилия должна быть заполнена")
    @Schema(description = "Фамилия студента", example = "Сидоров")
    String firstName;

    @NotBlank(message = "имя должно быть заполнено")
    @Schema(description = "Имя студента", example = "Петр")
    String middleName;

    @Schema(description = "Отчество студента", example = "Петрович")
    String lastName;

    @NotNull(message = "группа должна быть указана")
    @Min(value = 1, message = "идентификатор группы должен быть больше 0")
    @Schema(description = "Идентификатор группы", example = "1")
    Long groupId;

    @NotNull(message = "статус должен быть указан")
    @Schema(description = "Статус студента", example = "ACTIVE")
    Status status;
}
