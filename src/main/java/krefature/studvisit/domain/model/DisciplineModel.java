package krefature.studvisit.domain.model;

import krefature.studvisit.common.enums.DisciplineName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DisciplineModel {

    private Long id;

    private DisciplineName name;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
