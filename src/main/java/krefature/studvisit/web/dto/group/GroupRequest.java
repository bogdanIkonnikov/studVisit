package krefature.studvisit.web.dto.group;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupRequest {
    @NotBlank(message = "название группы должно быть заполнено")
    private String groupName;
}
