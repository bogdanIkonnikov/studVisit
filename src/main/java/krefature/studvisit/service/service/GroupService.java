package krefature.studvisit.service.service;

import krefature.studvisit.DAO.repository.GroupModelRepository;
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
        return repository.findById(id);
    }

    public GroupModel addGroup(String groupName) {
        GroupModel model = new GroupModel();
        model.setName(groupName);
        return repository.save(model);
    }

    public GroupModel editGroup(GroupModel uModel) {
        GroupModel model = repository.findById(uModel.getId());
        model.setName(uModel.getName());
        return repository.save(model);
    }

    public void deleteGroup(Long id) {
        repository.deleteById(id);
    }
}
