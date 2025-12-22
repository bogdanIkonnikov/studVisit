package krefature.studvisit.web.controller;

import krefature.studvisit.service.model.GroupModel;
import krefature.studvisit.web.dto.group.GroupRequest;
import krefature.studvisit.web.dto.group.GroupResponse;
import krefature.studvisit.service.service.GroupService;
import krefature.studvisit.web.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupMapper mapper;

    @GetMapping("/getAll")
    public List<GroupResponse> getStudentGroups() {
        return mapper.toResponses(groupService.getAllGroups());
    }

    @GetMapping("/{id}/get")
    public GroupResponse getStudentGroupById(@PathVariable Long Id) {
        return mapper.toResponse(groupService.getGroupById(Id));
    }

    @PostMapping("/add")
    public GroupResponse addStudentGroup(@RequestBody String groupName) {
        return mapper.toResponse(groupService.addGroup(groupName));
    }

    @PutMapping("/{id}/edit")
    public GroupResponse editStudentGroup(@RequestBody GroupRequest groupRequest, @PathVariable Long id) {
        GroupModel groupModel = mapper.toModel(groupRequest, id);
        return mapper.toResponse(groupService.editGroup(groupModel));
    }

    @DeleteMapping("/{id}/delete")
    public void deleteStudentGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }
}
