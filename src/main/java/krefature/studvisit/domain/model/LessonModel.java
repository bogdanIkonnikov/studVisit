package krefature.studvisit.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LessonModel {
    private Long id;

    private String date;

    private int time;

    private Long teacherId;

    private Long groupId;

    private Long disciplineId;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
