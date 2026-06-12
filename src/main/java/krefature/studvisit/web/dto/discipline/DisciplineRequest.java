package krefature.studvisit.web.dto.discipline;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.enums.DisciplineName;
import lombok.Data;

@Data
public class DisciplineRequest {
    @NotNull(message = "Название дисциплины не должно быть пустым")
    @Schema(description = "Название дисциплины", example = "MATH")
    private DisciplineName name;
}
