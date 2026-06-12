package krefature.studvisit.infrastructure.repository;

import krefature.studvisit.infrastructure.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> getStudentsByGroupId(Long groupId);
    Student getById(Long id);
    void deleteById(Long id);
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.firstName = :f AND s.middleName = :i AND s.lastName = :o")
    boolean existsByFIO(String f, String i, String o);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.firstName = :f AND s.middleName = :i AND s.lastName = :o AND s.id <> :id")
    boolean existsByFIOAndIdNot(String f, String i, String o, Long id);
}
