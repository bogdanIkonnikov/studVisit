package krefature.studvisit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import krefature.studvisit.common.enums.DisciplineName;
import krefature.studvisit.common.enums.UserRole;
import krefature.studvisit.infrastructure.entity.AppUser;
import krefature.studvisit.infrastructure.entity.Discipline;
import krefature.studvisit.infrastructure.entity.Group;
import krefature.studvisit.infrastructure.entity.Lesson;
import krefature.studvisit.infrastructure.entity.Teacher;
import krefature.studvisit.infrastructure.repository.AppUserRepository;
import krefature.studvisit.infrastructure.repository.DisciplineRepository;
import krefature.studvisit.infrastructure.repository.GroupRepository;
import krefature.studvisit.infrastructure.repository.LessonRepository;
import krefature.studvisit.infrastructure.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StudVisitIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void cleanDatabase() {
        lessonRepository.deleteAll();
        teacherRepository.deleteAll();
        disciplineRepository.deleteAll();
        groupRepository.deleteAll();
        appUserRepository.deleteAll();
    }

    @Test
    void loginReturnsAccessAndRefreshTokensForBootstrapAdmin() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "admin",
                                  "password": "admin123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.data.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.data.role").value("ADMIN"))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    @Test
    void teachersEndpointReturnsPagedPayloadForAuthorizedAdmin() throws Exception {
        teacherRepository.save(createTeacher("Иванов", "Иван", "Иванович"));
        teacherRepository.save(createTeacher("Петров", "Петр", "Петрович"));

        mockMvc.perform(get("/api/teachers")
                        .param("page", "0")
                        .param("size", "1")
                        .header("Authorization", bearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.items.length()").value(1))
                .andExpect(jsonPath("$.data.page").value(0))
                .andExpect(jsonPath("$.data.size").value(1))
                .andExpect(jsonPath("$.data.totalElements").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(2));
    }

    @Test
    void deletingDisciplineLinkedToLessonReturnsConflict() throws Exception {
        Teacher teacher = teacherRepository.save(createTeacher("Иванов", "Иван", "Иванович"));
        Group group = groupRepository.save(createGroup("ИС-101"));
        Discipline discipline = disciplineRepository.save(createDiscipline(DisciplineName.MATH));
        lessonRepository.save(createLesson(teacher, group, discipline, "2026-06-20", 9));

        mockMvc.perform(delete("/api/disciplines/{id}/delete", discipline.getId())
                        .header("Authorization", bearerToken()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(409))
                .andExpect(jsonPath("$.errorMessage").value("Нельзя удалить дисциплину, пока с ней связаны занятия."));
    }

    @Test
    void lessonCrudFlowWorksForAuthorizedAdmin() throws Exception {
        Teacher teacher = teacherRepository.save(createTeacher("Сидоров", "Сидор", "Сидорович"));
        Group group = groupRepository.save(createGroup("ИС-202"));
        Discipline discipline = disciplineRepository.save(createDiscipline(DisciplineName.ENGLISH));

        MvcResult createResult = mockMvc.perform(post("/api/lessons/add")
                        .header("Authorization", bearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "teacherId": %d,
                                  "groupId": %d,
                                  "disciplineId": %d,
                                  "date": "2026-06-20",
                                  "time": 9
                                }
                                """.formatted(teacher.getId(), group.getId(), discipline.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.teacherId").value(teacher.getId()))
                .andReturn();

        Long lessonId = readId(createResult);

        mockMvc.perform(put("/api/lessons/{id}/edit", lessonId)
                        .header("Authorization", bearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "teacherId": %d,
                                  "groupId": %d,
                                  "disciplineId": %d,
                                  "date": "2026-06-21",
                                  "time": 10
                                }
                                """.formatted(teacher.getId(), group.getId(), discipline.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(lessonId))
                .andExpect(jsonPath("$.data.date").value("2026-06-21"))
                .andExpect(jsonPath("$.data.time").value(10));

        mockMvc.perform(delete("/api/lessons/{id}/delete", lessonId)
                        .header("Authorization", bearerToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        assertThat(lessonRepository.existsById(lessonId)).isFalse();
    }

    @Test
    void refreshReturnsNewTokensForValidRefreshToken() throws Exception {
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "admin",
                                  "password": "admin123"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        String refreshToken = objectMapper.readTree(loginResult.getResponse().getContentAsString())
                .path("data")
                .path("refreshToken")
                .asText();

        mockMvc.perform(post("/api/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "refreshToken": "%s"
                                }
                                """.formatted(refreshToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.data.refreshToken").isNotEmpty())
                .andExpect(jsonPath("$.data.role").value("ADMIN"));
    }

    @Test
    void teacherCannotCreateDisciplineBecauseEndpointIsAdminOnly() throws Exception {
        createUser("teacher", "teacher123", UserRole.TEACHER);

        mockMvc.perform(post("/api/disciplines/add")
                        .header("Authorization", bearerToken("teacher", "teacher123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "OTHER"
                                }
                                """))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(403));
    }

    @Test
    void duplicateTeacherFioReturnsConflict() throws Exception {
        teacherRepository.save(createTeacher("Иванов", "Иван", "Иванович"));

        mockMvc.perform(post("/api/teachers/add")
                        .header("Authorization", bearerToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Иванов",
                                  "middleName": "Иван",
                                  "lastName": "Иванович"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(409))
                .andExpect(jsonPath("$.errorMessage").value("Преподаватель с таким ФИО уже существует."));
    }

    private String bearerToken() throws Exception {
        return bearerToken("admin", "admin123");
    }

    private String bearerToken(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "password": "%s"
                                }
                                """.formatted(username, password)))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return "Bearer " + json.path("data").path("accessToken").asText();
    }

    private Long readId(MvcResult result) throws Exception {
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        return json.path("data").path("id").asLong();
    }

    private Teacher createTeacher(String firstName, String middleName, String lastName) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setMiddleName(middleName);
        teacher.setLastName(lastName);
        return teacher;
    }

    private Group createGroup(String name) {
        Group group = new Group();
        group.setName(name);
        return group;
    }

    private Discipline createDiscipline(DisciplineName name) {
        Discipline discipline = new Discipline();
        discipline.setName(name);
        return discipline;
    }

    private Lesson createLesson(Teacher teacher, Group group, Discipline discipline, String date, int time) {
        Lesson lesson = new Lesson();
        lesson.setTeacher(teacher);
        lesson.setGroup(group);
        lesson.setDiscipline(discipline);
        lesson.setDate(date);
        lesson.setTime(time);
        return lesson;
    }

    private void createUser(String username, String rawPassword, UserRole role) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setEnabled(true);
        appUserRepository.save(user);
    }
}
