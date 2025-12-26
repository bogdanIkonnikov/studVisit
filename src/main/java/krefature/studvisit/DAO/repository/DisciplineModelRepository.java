package krefature.studvisit.DAO.repository;

import krefature.studvisit.service.model.DisciplineModel;

import java.util.List;

public interface DisciplineModelRepository {
    List<DisciplineModel> findAll();

    DisciplineModel findById(Long id);

    DisciplineModel save(DisciplineModel model);

    void deleteById(Long id);
}
