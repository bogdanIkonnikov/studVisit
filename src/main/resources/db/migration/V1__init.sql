CREATE TABLE groups (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE,
                        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE teachers (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          middle_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE disciplines (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL UNIQUE, -- Хранит строковое представление твоего Enum DisciplineName
                             created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE students (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          middle_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          status VARCHAR(50) NOT NULL,       -- Хранит строковое представление твоего Enum Status
                          group_id BIGINT,
                          created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_students_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE SET NULL
);

CREATE TABLE lesson (
                        id BIGSERIAL PRIMARY KEY,
                        date VARCHAR(255) NOT NULL,        -- В соответствии с типом String date в твоей Entity
                        time INT NOT NULL,
                        teacher_id BIGINT,
                        group_id BIGINT,
                        discipline_id BIGINT,
                        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_lesson_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE SET NULL,
                        CONSTRAINT fk_lesson_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE SET NULL,
                        CONSTRAINT fk_lesson_discipline FOREIGN KEY (discipline_id) REFERENCES disciplines(id) ON DELETE SET NULL
);

CREATE TABLE lesson_visit (
                              id BIGSERIAL PRIMARY KEY,
                              lesson_id BIGINT,
                              CONSTRAINT fk_lesson_visit_lesson FOREIGN KEY (lesson_id) REFERENCES lesson(id) ON DELETE CASCADE
);

CREATE TABLE lesson_visit_student (
                                      lesson_visit_id BIGINT NOT NULL,
                                      student_id BIGINT NOT NULL,
                                      PRIMARY KEY (lesson_visit_id, student_id),
                                      CONSTRAINT fk_lvs_visit FOREIGN KEY (lesson_visit_id) REFERENCES lesson_visit(id) ON DELETE CASCADE,
                                      CONSTRAINT fk_lvs_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);
