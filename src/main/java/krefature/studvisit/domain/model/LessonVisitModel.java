package krefature.studvisit.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LessonVisitModel {
    private Long id;

    private Long lessonId;

    private List<Long> studentIds = new ArrayList<>();
}
