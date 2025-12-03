package krefature.studvisit.repository;

import krefature.studvisit.entity.LessonVisit;
import krefature.studvisit.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonVisitRepository extends JpaRepository<LessonVisit, Long> {
    boolean addStudents(List<Long> studentsId);
    boolean deleteStudents(List<Long> studentsId);
}
