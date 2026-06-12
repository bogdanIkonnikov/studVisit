package krefature.studvisit.domain.repository;

import krefature.studvisit.domain.model.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonModelRepository {

    List<LessonModel> getAllByTeacherIdAndDateBetween(Long teacherId, String dateBefore, String dateAfter);

    List<LessonModel> getAllByGroupIdAndDateBetween(Long groupId, String dateBefore, String dateAfter);

    void deleteAllByTeacherId(Long teacherId);

    void deleteAllByGroupId(Long groupId);

    void deleteById(Long id);

    LessonModel save(LessonModel model);

    LessonModel findById(Long id);

    boolean existsById(Long id);

    boolean existsByTeacherId(Long teacherId);

    boolean existsByDisciplineId(Long disciplineId);

    Page<LessonModel> getPageByTeacherIdAndDateBetween(Long teacherId, String dateAfter, String dateBefore, Pageable pageable);

    Page<LessonModel> getPageByGroupIdAndDateBetween(Long groupId, String dateAfter, String dateBefore, Pageable pageable);
}
