package krefature.studvisit.domain.repository;

import krefature.studvisit.domain.model.LessonVisitModel;
import krefature.studvisit.infrastructure.entity.LessonVisit;

import java.util.List;

public interface LessonVisitModelRepository {
    LessonVisitModel save(LessonVisitModel model);

    LessonVisitModel findById(Long id);

    List<LessonVisitModel> findAll();

    void deleteById(Long id);

    LessonVisitModel findByLessonId(Long lessonId);

    boolean existsById(Long id);

    List<LessonVisitModel> findAllByStudentId(Long studentId);

}

