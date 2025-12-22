package krefature.studvisit.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
public class GroupModel {
    private Long id;

    private String name;

    private String created_at;

    private String updated_at;
}
