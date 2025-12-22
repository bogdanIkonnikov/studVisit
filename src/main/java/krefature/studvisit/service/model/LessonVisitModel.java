package krefature.studvisit.service.model;

import krefature.studvisit.repository.entity.Lesson;
import krefature.studvisit.repository.entity.Student;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@NoArgsConstructor
public class LessonVisitModel {
    private Long id;

    private Lesson lesson;

    private List<Long> studentIds = new ArrayList<>();
}
