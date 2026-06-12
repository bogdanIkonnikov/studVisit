ALTER TABLE app_users
DROP CONSTRAINT IF EXISTS fk_app_users_student;

ALTER TABLE app_users
DROP COLUMN IF EXISTS student_id;
