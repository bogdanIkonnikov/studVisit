package krefature.studvisit.DAO.repository;

import krefature.studvisit.service.model.StudentModel;

import java.util.List;

public interface StudentModelRepository {

    List<StudentModel> getStudentsByGroupId(Long groupId);

    StudentModel findById(Long studentId);

    StudentModel save(StudentModel cModel);

    void deleteById(Long studentId);
}
