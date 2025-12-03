package krefature.studvisit.repository;

import krefature.studvisit.entity.Group;
import krefature.studvisit.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findById(Long id);
    void deleteById(Long id);
}
