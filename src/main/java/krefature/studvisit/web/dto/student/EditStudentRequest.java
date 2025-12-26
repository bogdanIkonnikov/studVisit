package krefature.studvisit.web.dto.student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.enums.Status;
import lombok.Data;

@Data
public class EditStudentRequest {
    @NotNull(message = "идентификатор студента должен быть указан")
    Long Id;
    @NotBlank(message = "фамилия должна быть заполнена")
    String firstName;
    @NotBlank(message = "имя должно быть заполнено")
    String middleName;
    String lastName;
    @NotNull(message = "группа должна быть указана")
    Long groupId;
    @NotNull(message = "статус должен быть указан")
    Status status;
}
