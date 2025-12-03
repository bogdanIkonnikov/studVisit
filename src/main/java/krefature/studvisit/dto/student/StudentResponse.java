package krefature.studvisit.dto.student;
import lombok.Data;


@Data
public class StudentResponse {
    private Long Id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String groupName;

    public StudentResponse(Long Id, String firstName, String middleName, String lastName, String groupName) {
        this.Id = Id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.groupName = groupName;
    }
}
