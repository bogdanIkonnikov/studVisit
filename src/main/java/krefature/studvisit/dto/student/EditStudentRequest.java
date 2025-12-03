package krefature.studvisit.dto.student;

import krefature.studvisit.enums.Status;
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
