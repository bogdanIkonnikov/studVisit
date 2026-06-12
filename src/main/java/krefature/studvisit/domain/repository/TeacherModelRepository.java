package krefature.studvisit.domain.repository;

import krefature.studvisit.domain.model.TeacherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherModelRepository {
    List<TeacherModel> findAll();

    TeacherModel findById(Long id);

    TeacherModel save(TeacherModel model);

    void delete(TeacherModel t);

    boolean existsById(Long id);

    boolean existsByFIO(String f, String i, String o);

    boolean existsByFIOAndIdNot(String f, String i, String o, Long id);

    Page<TeacherModel> findAll(Pageable pageable);
}
