package krefature.studvisit.web.dto.lesson;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LessonResponse {
    private String date;
    private int time;
    private String teacherFIO;
}
