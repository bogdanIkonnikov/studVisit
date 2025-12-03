package krefature.studvisit.service;

import krefature.studvisit.dto.student.CreateStudentRequest;
import krefature.studvisit.dto.student.StudentResponse;
import krefature.studvisit.dto.student.EditStudentRequest;
import krefature.studvisit.entity.Group;
import krefature.studvisit.entity.Student;
import krefature.studvisit.mapper.StudentMapper;
import krefature.studvisit.repository.GroupRepository;
import krefature.studvisit.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentMapper studentMapper;

    public List<StudentResponse> getStudentsByGroup(Long groupId) {
        List<Student> students = studentRepository.getAllByGroup(groupId);
        List<StudentResponse> studentResponses = new ArrayList<>();
        for (Student student : students) {
            StudentResponse response = studentMapper.toResponse(student);
            studentResponses.add(response);
        }
        return studentResponses;
    }

    public StudentResponse getStudentById(Long studentId) {
        Student student = studentRepository.getById(studentId);
        return studentMapper.toResponse(student);
    }

    public StudentResponse addStudent(CreateStudentRequest request) {
        Student student = studentMapper.toEntity(request, groupRepository.findById(request.getGroupId()).orElse(null));
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponse(savedStudent);
    }

    public StudentResponse updateStudent(EditStudentRequest request) {
        Student student = studentRepository.getById(request.getId());
        Group group = groupRepository.getById(request.getGroupId());
        studentMapper.updateEntity(request, student, group);
        studentRepository.update(student);
        return studentMapper.toResponse(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }


}
