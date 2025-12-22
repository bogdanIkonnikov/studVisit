package krefature.studvisit.web.dto.student;
import krefature.studvisit.common.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StudentResponse {
    private Long Id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long groupId;
    private Status status;
}
