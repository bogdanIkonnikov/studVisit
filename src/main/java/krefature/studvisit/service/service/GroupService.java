package krefature.studvisit.service.service;

import krefature.studvisit.DAO.repository.GroupModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.service.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupModelRepository repository;

    public List<GroupModel> getAllGroups() {
        return repository.findAll();
    }

    public GroupModel getGroupById(Long id) {
        GroupModel model = repository.findById(id);
        if (model == null) throw new NotFoundException("Группа", id);
        return model;
    }

    public GroupModel addGroup(GroupModel group) {
        GroupModel model = new GroupModel();
        model.setName(group.getName());
        return repository.save(model);
    }

    public GroupModel editGroup(GroupModel uModel) {
        GroupModel model = repository.findById(uModel.getId());
        if (model == null) throw new NotFoundException("Группа", uModel.getId());
        model.setName(uModel.getName());
        return repository.save(model);
    }

    public void deleteGroup(Long id) {
        GroupModel model = repository.findById(id);
        if (model == null) throw new NotFoundException("Группа", id);
        repository.deleteById(id);
    }
}
