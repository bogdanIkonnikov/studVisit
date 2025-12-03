package krefature.studvisit.service;

import krefature.studvisit.dto.group.EditGroupRequest;
import krefature.studvisit.dto.group.GroupResponse;
import krefature.studvisit.entity.Group;
import krefature.studvisit.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        List<GroupResponse> groupResponses = new ArrayList<GroupResponse>();
        for (Group group : groups) {
            GroupResponse groupResponse = new GroupResponse();
            groupResponse.setId(group.getId());
            groupResponse.setGroupName(group.getName());
        }
        return groupResponses;
    }

    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id).get();
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupName(group.getName());
        groupResponse.setId(group.getId());
        return groupResponse;
    }

    public GroupResponse addGroup(String groupName) {
        Group group = new Group();
        group.setName(groupName);
        groupRepository.save(group);
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupName(group.getName());
        groupResponse.setId(group.getId());
        return groupResponse;
    }

    public GroupResponse editGroup(EditGroupRequest request) {
        Group group = groupRepository.findById(request.getGroupId()).get();
        group.setName(request.getGroupName());
        groupRepository.save(group);
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setGroupName(group.getName());
        groupResponse.setId(group.getId());
        return groupResponse;
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
