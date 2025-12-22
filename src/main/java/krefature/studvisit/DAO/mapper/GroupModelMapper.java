package krefature.studvisit.DAO.mapper;

import krefature.studvisit.repository.entity.Group;
import krefature.studvisit.service.model.GroupModel;
import org.springframework.stereotype.Component;

@Component
public class GroupModelMapper {
    public GroupModel toModel(Group group){
        GroupModel groupModel = new GroupModel();
        groupModel.setId(group.getId());
        groupModel.setName(group.getName());
        return groupModel;
    }
    public Group toEntity(GroupModel groupModel){
        Group group = new Group();
        group.setId(groupModel.getId());
        group.setName(groupModel.getName());
        return group;
    }
}
