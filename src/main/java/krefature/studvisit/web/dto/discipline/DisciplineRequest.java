package krefature.studvisit.web.dto.discipline;

import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.enums.DisciplineName;
import lombok.Data;

@Data
public class DisciplineRequest {
    @NotNull(message = "Название дисциплины не должно быть пустым")
    private DisciplineName name;
}
