package krefature.studvisit.controller;

import krefature.studvisit.dto.group.EditGroupRequest;
import krefature.studvisit.dto.group.GroupResponse;
import krefature.studvisit.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("/getStudentGroups")
    public List<GroupResponse> getStudentGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping("/getStudentGroupById")
    public GroupResponse getStudentGroupById(@RequestBody Long Id) {
        return groupService.getGroupById(Id);
    }

    @PostMapping("/addStudentGroup")
    public GroupResponse addStudentGroup(@RequestBody String groupName) {
        return groupService.addGroup(groupName);
    }
    //ResponseEntity
    @PostMapping("/editStudentGroup")
    public GroupResponse editStudentGroup(@RequestBody EditGroupRequest editGroupRequest) {
        return groupService.editGroup(editGroupRequest);
    }

    @PostMapping("/deleteStudentGroup")
    public void deleteStudentGroup(@RequestBody Long Id) {
        groupService.deleteGroup(Id);
    }
}
