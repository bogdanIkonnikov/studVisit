package krefature.studvisit.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GroupModel {
    private Long id;

    private String name;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
