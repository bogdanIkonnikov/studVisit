package krefature.studvisit.repository.repository;

import krefature.studvisit.repository.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    void deleteById(Long id);
}
