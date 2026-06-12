package krefature.studvisit.web.dto.discipline;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DisciplineResponse {
    @Schema(description = "Идентификатор дисциплины", example = "1")
    private Long id;

    @Schema(description = "Название дисциплины", example = "MATH")
    private String name;
}
