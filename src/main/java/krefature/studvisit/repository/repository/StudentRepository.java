package krefature.studvisit.repository.repository;

import krefature.studvisit.repository.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> getStudentsByGroupId(Long groupId);
    Student getById(Long id);
    void deleteById(Long id);
}
