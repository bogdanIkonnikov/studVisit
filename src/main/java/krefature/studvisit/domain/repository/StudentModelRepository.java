package krefature.studvisit.domain.repository;

import krefature.studvisit.domain.model.StudentModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentModelRepository {

    List<StudentModel> getStudentsByGroupId(Long groupId);

    StudentModel findById(Long studentId);

    StudentModel save(StudentModel cModel);

    void deleteById(Long studentId);

    boolean existsById(Long id);

    boolean existsByFIO(String f, String i, String o);

    boolean existsByFIOAndIdNot(String f, String i, String o, Long id);
}
