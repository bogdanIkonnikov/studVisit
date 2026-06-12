package krefature.studvisit.web.dto.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupRequest {
    @NotBlank(message = "название группы должно быть заполнено")
    @Schema(description = "Название группы", example = "М-101")
    private String groupName;
}
