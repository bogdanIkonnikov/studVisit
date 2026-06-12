package krefature.studvisit.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TeacherModel {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
