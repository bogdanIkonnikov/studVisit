package krefature.studvisit.web.dto.student;

import krefature.studvisit.common.enums.Status;
import lombok.Data;

@Data
public class CreateStudentRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private Long groupId;
    private Status status;
}
