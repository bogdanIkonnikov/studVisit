package krefature.studvisit.DAO.mapper;

import krefature.studvisit.repository.entity.Student;
import krefature.studvisit.repository.repository.GroupRepository;
import krefature.studvisit.service.model.StudentModel;
import org.springframework.stereotype.Component;

@Component
public class StudentModelMapper {
    private final GroupRepository groupRepository;

    public StudentModelMapper(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public StudentModel toModel(Student student){
        StudentModel studentModel = new StudentModel();
        studentModel.setId(student.getId());
        studentModel.setFirstName(student.getFirstName());
        studentModel.setLastName(student.getLastName());
        studentModel.setMiddleName(student.getMiddleName());
        studentModel.setStatus(student.getStatus());
        if(student.getGroup()!=null){
            studentModel.setGroupId(student.getGroup().getId());
        }
        return studentModel;
    }
    public Student toEntity(StudentModel studentModel){
        Student student = new Student();
        student.setId(studentModel.getId());
        student.setFirstName(studentModel.getFirstName());
        student.setLastName(studentModel.getLastName());
        student.setMiddleName(studentModel.getMiddleName());
        student.setStatus(studentModel.getStatus());
        student.setGroup(groupRepository.findById(studentModel.getGroupId()).get());
        return student;
    }
}
