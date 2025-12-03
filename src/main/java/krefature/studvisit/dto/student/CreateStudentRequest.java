package krefature.studvisit.dto.student;

import krefature.studvisit.enums.Status;
import lombok.Getter;

@Getter
public class CreateStudentRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private Long groupId;
    private Status status;
}
