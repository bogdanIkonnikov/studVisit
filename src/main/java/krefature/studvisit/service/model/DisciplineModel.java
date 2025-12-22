package krefature.studvisit.service.model;

import krefature.studvisit.common.enums.DisciplineName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class DisciplineModel {
    private Long id;

    private DisciplineName name;

    private String created_at;

    private String updated_at;
}
