package krefature.studvisit.controller;

import krefature.studvisit.dto.group.GroupRequest;
import krefature.studvisit.dto.group.GroupResponse;
import krefature.studvisit.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/getAll")
    public List<GroupResponse> getStudentGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping("/{id}/get")
    public GroupResponse getStudentGroupById(@PathVariable Long Id) {
        return groupService.getGroupById(Id);
    }

    @PostMapping("/add")
    public GroupResponse addStudentGroup(@RequestBody String groupName) {
        return groupService.addGroup(groupName);
    }

    @PostMapping("/{id}/edit")
    public GroupResponse editStudentGroup(@RequestBody GroupRequest groupRequest, @PathVariable Long id) {
        return groupService.editGroup(groupRequest, id);
    }

    @PostMapping("/{id}/delete")
    public void deleteStudentGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }
}
