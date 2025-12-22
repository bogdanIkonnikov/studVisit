package krefature.studvisit.web.dto.lessonVisit;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessonVisitResponse {
    private String name;
    private List<String> studentsFIO;
}
