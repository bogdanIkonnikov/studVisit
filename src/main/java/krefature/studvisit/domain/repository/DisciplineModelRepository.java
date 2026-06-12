package krefature.studvisit.domain.repository;

import krefature.studvisit.domain.model.DisciplineModel;

import java.util.List;

public interface DisciplineModelRepository {
    List<DisciplineModel> findAll();

    DisciplineModel findById(Long id);

    DisciplineModel save(DisciplineModel model);

    void deleteById(Long id);

    boolean existsById(Long id);
}
