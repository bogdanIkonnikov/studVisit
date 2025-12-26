package krefature.studvisit.web.mapper;

import krefature.studvisit.service.model.GroupModel;
import krefature.studvisit.web.dto.group.GroupRequest;
import krefature.studvisit.web.dto.group.GroupResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupMapper {
    public GroupResponse toResponse(GroupModel model) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(model.getId());
        groupResponse.setGroupName(model.getName());
        return groupResponse;
    }
    public List<GroupResponse> toResponses(List<GroupModel> model) {
        return model.stream().map(this::toResponse).toList();
    }

    public GroupModel toModel(GroupRequest groupRequest, Long id) {
        GroupModel model = new GroupModel();
        model.setId(id);
        model.setName(groupRequest.getGroupName());
        return model;
    }
    public GroupModel toModel(GroupRequest groupRequest) {
        GroupModel model = new GroupModel();
        model.setName(groupRequest.getGroupName());
        return model;
    }
}
