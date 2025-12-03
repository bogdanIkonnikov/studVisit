package krefature.studvisit.entity;

import jakarta.persistence.*;
import krefature.studvisit.enums.DisciplineName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "disciplines")
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private DisciplineName name;

    @Column(nullable = false)
    private String created_at;
    @Column(nullable = false)
    private String updated_at;

    public Discipline(DisciplineName name) {
        this.name = name;
    }
}
