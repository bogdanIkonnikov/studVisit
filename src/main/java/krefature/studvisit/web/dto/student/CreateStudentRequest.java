package krefature.studvisit.web.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.enums.Status;
import lombok.Data;

@Data
public class CreateStudentRequest {
    @NotBlank(message = "фамилия должна быть заполнена")
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank(message = "имя должно быть заполнено")
    private String middleName;
    @NotNull(message = "группа должна быть указана")
    private Long groupId;
    @NotNull(message = "статус должен быть указан")
    private Status status;
}
