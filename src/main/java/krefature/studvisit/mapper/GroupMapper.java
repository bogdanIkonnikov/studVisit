package krefature.studvisit.mapper;

import krefature.studvisit.dto.group.GroupRequest;
import krefature.studvisit.dto.group.GroupResponse;
import krefature.studvisit.entity.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public Group toEntity(String groupName) {
        Group group = new Group();
        group.setName(groupName);
        return group;
    }
    public GroupResponse toResponse(Group group) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(group.getId());
        groupResponse.setGroupName(group.getName());
        return groupResponse;
    }
    public void updateEntity(Group group, GroupRequest groupRequest) {
        group.setName(groupRequest.getGroupName());
    }
}
