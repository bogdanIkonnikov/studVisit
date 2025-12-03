package krefature.studvisit.dto.lessonVisit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonVisitResponse {
    private String name;
    private List<String> studentsFIO;
}
