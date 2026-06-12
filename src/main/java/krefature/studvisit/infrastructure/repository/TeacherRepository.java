package krefature.studvisit.infrastructure.repository;

import krefature.studvisit.infrastructure.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Teacher s WHERE s.firstName = :f AND s.middleName = :i AND s.lastName = :o")
    boolean existsByFIO(String f, String i, String o);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Teacher s WHERE s.firstName = :f AND s.middleName = :i AND s.lastName = :o AND s.id <> :id")
    boolean existsByFIOAndIdNot(String f, String i, String o, Long id);

    Page<Teacher> findAll(Pageable pageable);
}
