package krefature.studvisit.web.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherRequest {
    @NotBlank(message = "фамилия должна быть заполнена")
    private String firstName;
    @NotBlank(message = "имя должно быть заполнено")
    private String middleName;
    private String lastName;
}
