package krefature.studvisit.service.model;

import krefature.studvisit.repository.entity.Group;
import krefature.studvisit.repository.entity.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class LessonModel {
    private Long id;

    private String date;

    private int time;

    private Long teacherId;

    private Long groupId;

    private String created_at;

    private String updated_at;
}
