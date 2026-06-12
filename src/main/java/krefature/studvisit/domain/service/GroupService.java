package krefature.studvisit.domain.service;

import krefature.studvisit.domain.repository.GroupModelRepository;
import krefature.studvisit.domain.repository.StudentModelRepository;
import krefature.studvisit.common.exceptions.NotFoundException;
import krefature.studvisit.common.exceptions.DependentEntityException;
import krefature.studvisit.domain.model.GroupModel;
import krefature.studvisit.domain.model.StudentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupModelRepository repository;
    @Autowired
    private StudentModelRepository studentModelRepository;

    public List<GroupModel> getAllGroups() {
        return repository.findAll();
    }

    public GroupModel getGroupById(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Группа", id);
        GroupModel model = repository.findById(id);
        return model;
    }

    public GroupModel addGroup(GroupModel group) {
        GroupModel model = new GroupModel();
        model.setName(group.getName());
        return repository.save(model);
    }

    public GroupModel editGroup(GroupModel uModel) {
        if (!repository.existsById(uModel.getId())) throw new NotFoundException("Группа", uModel.getId());
        GroupModel model = repository.findById(uModel.getId());
        model.setName(uModel.getName());
        return repository.save(model);
    }

    public void deleteGroup(Long id) {
        if (!repository.existsById(id)) throw new NotFoundException("Группа", id);

        List<StudentModel> students = studentModelRepository.getStudentsByGroupId(id);
        if (students != null && !students.isEmpty()) {
            java.util.List<String> details = new java.util.ArrayList<>();
            details.add("В группе ещё остались " + students.size() + " студент(ов)");
            throw new DependentEntityException(
                    "Невозможно удалить группу: в ней остались студенты. Удалите сначала всех студентов.",
                    "Group", id, details
            );
        }
        repository.deleteById(id);
    }
}
