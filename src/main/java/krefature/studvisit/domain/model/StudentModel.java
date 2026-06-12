package krefature.studvisit.domain.model;

import krefature.studvisit.common.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentModel {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private Status status;

    private Long groupId;

    private List<Long> lessonVisitIds = new ArrayList<>();

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
