package krefature.studvisit.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import krefature.studvisit.service.model.GroupModel;
import krefature.studvisit.web.dto.group.GroupRequest;
import krefature.studvisit.web.dto.group.GroupResponse;
import krefature.studvisit.service.service.GroupService;
import krefature.studvisit.web.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@Validated
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupMapper mapper;

    @GetMapping("/getAll")
    public List<GroupResponse> getStudentGroups() {
        return mapper.toResponses(groupService.getAllGroups());
    }

    @GetMapping("/{id}")
    public GroupResponse getStudentGroupById(@NotNull(message = "id должен быть указан") @Min(value = 1, message = "id должен быть больше 0") @PathVariable Long id) {
        return mapper.toResponse(groupService.getGroupById(id));
    }

    @PostMapping("/add")
    public GroupResponse addStudentGroup(@Valid @RequestBody GroupRequest groupRequest) {
        return mapper.toResponse(groupService.addGroup(mapper.toModel(groupRequest)));
    }

    @PutMapping("/{id}/edit")
    public GroupResponse editStudentGroup(@Valid @RequestBody GroupRequest groupRequest, @NotNull @PathVariable Long id) {
        GroupModel groupModel = mapper.toModel(groupRequest, id);
        return mapper.toResponse(groupService.editGroup(groupModel));
    }

    @DeleteMapping("/{id}/delete")
    public void deleteStudentGroup(@NotNull(message = "id должен быть указан") @Min(value = 1, message = "id должен быть больше 0") @PathVariable Long id) {
        groupService.deleteGroup(id);
    }
}
