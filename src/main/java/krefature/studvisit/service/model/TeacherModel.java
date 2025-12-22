package krefature.studvisit.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class TeacherModel {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String created_at;

    private String updated_at;
}
