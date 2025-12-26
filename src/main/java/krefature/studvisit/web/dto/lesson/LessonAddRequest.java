package krefature.studvisit.web.dto.lesson;

import lombok.Data;

@Data
public class LessonAddRequest {
    private Long teacherId;
    private String Date;
    private Long groupId;
    private int time;
}
