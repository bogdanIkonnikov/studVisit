package krefature.studvisit.dto.student;
import krefature.studvisit.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StudentResponse {
    private Long Id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String groupName;
    private Status status;
}
