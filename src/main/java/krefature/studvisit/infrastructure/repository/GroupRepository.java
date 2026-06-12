package krefature.studvisit.infrastructure.repository;

import krefature.studvisit.infrastructure.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    void deleteById(Long id);
}
