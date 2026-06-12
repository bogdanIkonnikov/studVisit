package krefature.studvisit.infrastructure.repository;

import krefature.studvisit.infrastructure.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByTeacherIdAndDateBetween(Long teacherId, String dateAfter, String dateBefore);
    List<Lesson> findAllByGroupIdAndDateBetween(Long groupId, String dateAfter, String dateBefore);
    Page<Lesson> findAllByTeacherIdAndDateBetween(Long teacherId, String dateAfter, String dateBefore, Pageable pageable);
    Page<Lesson> findAllByGroupIdAndDateBetween(Long groupId, String dateAfter, String dateBefore, Pageable pageable);
    long deleteAllByTeacherId(Long teacherId);
    long deleteAllByGroupId(Long groupId);
    boolean existsByTeacherId(Long teacherId);
    boolean existsByDisciplineId(Long disciplineId);

}
