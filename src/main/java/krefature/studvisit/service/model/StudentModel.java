package krefature.studvisit.service.model;

import krefature.studvisit.common.enums.Status;
import krefature.studvisit.repository.entity.LessonVisit;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    private String created_at;

    private String updated_at;
}
