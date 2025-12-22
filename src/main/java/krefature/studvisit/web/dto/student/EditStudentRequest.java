package krefature.studvisit.web.dto.student;

import krefature.studvisit.common.enums.Status;
import lombok.Data;

@Data
public class EditStudentRequest {
    Long Id;
    String firstName;
    String middleName;
    String lastName;
    Long groupId;
    Status status;
}
