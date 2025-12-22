package krefature.studvisit.DAO.repository;

import krefature.studvisit.service.model.TeacherModel;

import java.util.List;

public interface TeacherModelRepository {
    List<TeacherModel> findAll();

    TeacherModel findById(Long id);

    TeacherModel save(TeacherModel model);

    void delete(TeacherModel t);
}
