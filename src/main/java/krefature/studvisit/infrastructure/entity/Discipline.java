package krefature.studvisit.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.common.enums.DisciplineName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(unique=true)
    private DisciplineName name;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updated_at;

}
