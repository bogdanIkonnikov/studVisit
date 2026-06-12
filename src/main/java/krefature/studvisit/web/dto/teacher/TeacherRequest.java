package krefature.studvisit.web.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherRequest {
    @NotBlank(message = "фамилия должна быть заполнена")
    @Schema(description = "Фамилия учителя", example = "Иванов")
    private String firstName;

    @NotBlank(message = "имя должно быть заполнено")
    @Schema(description = "Имя учителя", example = "Иван")
    private String middleName;

    @Schema(description = "Отчество учителя", example = "Иванович")
    private String lastName;
}
