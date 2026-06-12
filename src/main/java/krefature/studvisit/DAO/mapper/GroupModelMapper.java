package krefature.studvisit.DAO.mapper;

import krefature.studvisit.infrastructure.entity.Group;
import krefature.studvisit.domain.model.GroupModel;
import org.springframework.stereotype.Component;

@Component
public class GroupModelMapper {
    public GroupModel toModel(Group group){
        if (group == null) return null;
        GroupModel groupModel = new GroupModel();
        groupModel.setId(group.getId());
        groupModel.setName(group.getName());
        groupModel.setCreated_at(group.getCreated_at());
        groupModel.setUpdated_at(group.getUpdated_at());
        return groupModel;
    }
    public Group toEntity(GroupModel groupModel){
        if (groupModel == null) return null;
        Group group = new Group();
        group.setId(groupModel.getId());
        group.setName(groupModel.getName());
        group.setCreated_at(groupModel.getCreated_at());
        group.setUpdated_at(groupModel.getUpdated_at());
        return group;
    }
}
