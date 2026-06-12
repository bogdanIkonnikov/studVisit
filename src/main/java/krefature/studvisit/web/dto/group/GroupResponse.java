package krefature.studvisit.web.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupResponse {
    @Schema(description = "Идентификатор группы", example = "1")
    private Long id;

    @Schema(description = "Название группы", example = "М-101")
    private String groupName;
}
