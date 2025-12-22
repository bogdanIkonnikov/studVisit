package krefature.studvisit.DAO.repository;

import krefature.studvisit.service.model.GroupModel;

import java.util.List;

public interface GroupModelRepository {
    List<GroupModel> findAll();

    GroupModel findById(Long id);

    GroupModel save(GroupModel model);

    void deleteById(Long id);
}
