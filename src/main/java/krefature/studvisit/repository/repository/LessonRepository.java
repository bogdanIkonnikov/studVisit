package krefature.studvisit.repository.repository;

import krefature.studvisit.repository.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByTeacherIdAndDateBetween(Long teacherId, String dateAfter, String dateBefore);
    List<Lesson> findAllByGroupIdAndDateBetween(Long groupId, String dateAfter, String dateBefore);
    Boolean deleteAllByTeacherId(Long teacherId);
    Boolean deleteAllByGroupId(Long groupId);
}
