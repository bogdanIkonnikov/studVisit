package krefature.studvisit.domain.repository;

import krefature.studvisit.domain.model.GroupModel;

import java.util.List;

public interface GroupModelRepository {
    List<GroupModel> findAll();

    GroupModel findById(Long id);

    GroupModel save(GroupModel model);

    void deleteById(Long id);

    boolean existsById(Long id);
}
