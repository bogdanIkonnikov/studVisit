package krefature.studvisit.repository.repository;

import krefature.studvisit.repository.entity.LessonVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonVisitRepository extends JpaRepository<LessonVisit, Long> {
}
