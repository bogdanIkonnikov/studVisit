package krefature.studvisit.infrastructure.repository;

import krefature.studvisit.domain.model.LessonVisitModel;
import krefature.studvisit.infrastructure.entity.LessonVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonVisitRepository extends JpaRepository<LessonVisit, Long> {
    LessonVisit findByLessonId(Long lessonId);
    @Query("SELECT lv FROM LessonVisit lv JOIN lv.students s WHERE s.id = :studentId")
    List<LessonVisit> findAllByStudentId(Long studentId);
}
