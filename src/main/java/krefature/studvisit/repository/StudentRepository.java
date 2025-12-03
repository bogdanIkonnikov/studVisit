package krefature.studvisit.repository;

import krefature.studvisit.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> getAllByGroup(Long groupId);
    Student getById(Long id);
    Student add(Student student);
    Student update(Student student);
    void deleteById(Long id);
}
