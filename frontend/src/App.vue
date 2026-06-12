<template>
  <div v-if="!session.isAuthenticated" class="auth-shell">
    <section class="auth-card">
      <div class="brand auth-brand">
        <span class="brand-mark">SV</span>
        <div>
          <strong>StudVisit</strong>
          <span>Вход в систему</span>
        </div>
      </div>

      <section v-if="notice.text" :class="['notice', notice.type]">
        <span>{{ notice.text }}</span>
        <button type="button" @click="clearNotice">Закрыть</button>
      </section>

      <form class="auth-form" @submit.prevent="login">
        <label>
          Логин
          <input v-model.trim="loginForm.username" autocomplete="username" required />
        </label>
        <label>
          Пароль
          <input v-model="loginForm.password" type="password" autocomplete="current-password" required />
        </label>
        <button type="submit" class="primary-button" :disabled="loading">
          Войти
        </button>
      </form>
    </section>
  </div>

  <div v-else class="app-shell">
    <aside class="sidebar">
      <div class="brand">
        <span class="brand-mark">SV</span>
        <div>
          <strong>StudVisit</strong>
          <span>Админ-панель</span>
        </div>
      </div>

      <nav class="nav-list" aria-label="Разделы админ-панели">
        <button
          v-for="tab in tabs"
          :key="tab.key"
          type="button"
          :class="{ active: activeTab === tab.key }"
          @click="activeTab = tab.key"
        >
          <span>{{ tab.icon }}</span>
          {{ tab.label }}
        </button>
      </nav>

      <div class="session-card">
        <strong>{{ session.username }}</strong>
        <span>{{ roleLabel(session.role) }}</span>
      </div>

      <button type="button" class="ghost-button" @click="loadAll">
        Обновить данные
      </button>
      <button type="button" class="ghost-button" @click="logout">
        Выйти
      </button>
    </aside>

    <main class="workspace">
      <header class="page-header">
        <div>
          <p class="eyebrow">Backend-first контракты</p>
          <h1>{{ activeTabMeta.label }}</h1>
        </div>
        <div class="status-card">
          <span>{{ counters.disciplines }}</span>
          дисциплин
        </div>
        <div class="status-card">
          <span>{{ counters.groups }}</span>
          групп
        </div>
        <div class="status-card">
          <span>{{ counters.teachers }}</span>
          учителей
        </div>
      </header>

      <section v-if="notice.text" :class="['notice', notice.type]">
        <span>{{ notice.text }}</span>
        <button type="button" @click="clearNotice">Закрыть</button>
      </section>

      <section v-if="loading" class="loading-panel">
        Загружаю данные...
      </section>

      <section v-show="activeTab === 'disciplines'" class="panel-grid">
        <form class="panel form-panel" @submit.prevent="saveDiscipline">
          <div class="panel-heading">
            <h2>{{ disciplineForm.id ? 'Редактировать дисциплину' : 'Новая дисциплина' }}</h2>
            <button v-if="disciplineForm.id" type="button" class="link-button" @click="resetDisciplineForm">
              Сбросить
            </button>
          </div>

          <label>
            Название
            <select v-model="disciplineForm.name" required>
              <option disabled value="">Выберите дисциплину</option>
              <option v-for="item in disciplineOptions" :key="item.value" :value="item.value">
                {{ item.label }}
              </option>
            </select>
          </label>

          <button type="submit" class="primary-button">
            {{ disciplineForm.id ? 'Сохранить' : 'Создать' }}
          </button>
        </form>

        <section class="panel table-panel">
          <div class="panel-heading">
            <h2>Список дисциплин</h2>
            <span>{{ disciplines.length }}</span>
          </div>

          <div class="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Название</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="discipline in disciplines" :key="discipline.id">
                  <td>{{ discipline.id }}</td>
                  <td>{{ disciplineLabel(discipline.name) }}</td>
                  <td class="actions">
                    <button type="button" @click="editDiscipline(discipline)">Править</button>
                    <button type="button" class="danger" @click="deleteDiscipline(discipline.id)">Удалить</button>
                  </td>
                </tr>
                <tr v-if="!disciplines.length">
                  <td colspan="3" class="empty-cell">Пока нет дисциплин</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
      </section>

      <section v-show="activeTab === 'groups'" class="panel-grid">
        <form class="panel form-panel" @submit.prevent="saveGroup">
          <div class="panel-heading">
            <h2>{{ groupForm.id ? 'Редактировать группу' : 'Новая группа' }}</h2>
            <button v-if="groupForm.id" type="button" class="link-button" @click="resetGroupForm">
              Сбросить
            </button>
          </div>

          <label>
            Название группы
            <input v-model.trim="groupForm.groupName" required placeholder="Например, ИС-101" />
          </label>

          <button type="submit" class="primary-button">
            {{ groupForm.id ? 'Сохранить' : 'Создать' }}
          </button>
        </form>

        <section class="panel table-panel">
          <div class="panel-heading">
            <h2>Список групп</h2>
            <span>{{ groups.length }}</span>
          </div>

          <div class="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Название</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="group in groups" :key="group.id">
                  <td>{{ group.id }}</td>
                  <td>{{ group.groupName }}</td>
                  <td class="actions">
                    <button type="button" @click="editGroup(group)">Править</button>
                    <button type="button" class="danger" @click="deleteGroup(group.id)">Удалить</button>
                  </td>
                </tr>
                <tr v-if="!groups.length">
                  <td colspan="3" class="empty-cell">Пока нет групп</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
      </section>

      <section v-show="activeTab === 'teachers'" class="panel-grid">
        <form class="panel form-panel" @submit.prevent="saveTeacher">
          <div class="panel-heading">
            <h2>{{ teacherForm.id ? 'Редактировать учителя' : 'Новый учитель' }}</h2>
            <button v-if="teacherForm.id" type="button" class="link-button" @click="resetTeacherForm">
              Сбросить
            </button>
          </div>

          <label>
            Фамилия
            <input v-model.trim="teacherForm.firstName" required placeholder="Иванов" />
          </label>
          <label>
            Имя
            <input v-model.trim="teacherForm.middleName" required placeholder="Иван" />
          </label>
          <label>
            Отчество
            <input v-model.trim="teacherForm.lastName" placeholder="Иванович" />
          </label>

          <button type="submit" class="primary-button">
            {{ teacherForm.id ? 'Сохранить' : 'Создать' }}
          </button>
        </form>

        <section class="panel table-panel">
          <div class="panel-heading">
            <h2>Список учителей</h2>
            <span>{{ teacherPage.totalElements }}</span>
          </div>

          <div class="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>ФИО</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="teacher in teacherPage.items" :key="teacher.id">
                  <td>{{ teacher.id }}</td>
                  <td>{{ teacher.teacherFIO }}</td>
                  <td class="actions">
                    <button type="button" @click="editTeacher(teacher)">Править</button>
                    <button type="button" class="danger" @click="deleteTeacher(teacher.id)">Удалить</button>
                  </td>
                </tr>
                <tr v-if="!teacherPage.items.length">
                  <td colspan="3" class="empty-cell">Пока нет учителей</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div class="pagination">
            <button type="button" @click="changeTeacherPage(-1)" :disabled="teacherPage.page <= 0">Назад</button>
            <span>Страница {{ teacherPage.page + 1 }} из {{ Math.max(teacherPage.totalPages, 1) }}</span>
            <button
              type="button"
              @click="changeTeacherPage(1)"
              :disabled="teacherPage.page + 1 >= teacherPage.totalPages"
            >
              Вперед
            </button>
          </div>
        </section>
      </section>

      <section v-show="activeTab === 'students'" class="panel-grid">
        <form class="panel form-panel" @submit.prevent="saveStudent">
          <div class="panel-heading">
            <h2>{{ studentForm.id ? 'Редактировать студента' : 'Новый студент' }}</h2>
            <button v-if="studentForm.id" type="button" class="link-button" @click="resetStudentForm">
              Сбросить
            </button>
          </div>

          <label>
            Группа
            <select v-model.number="selectedGroupId" required @change="onStudentGroupChange">
              <option disabled value="">Выберите группу</option>
              <option v-for="group in groups" :key="group.id" :value="group.id">
                {{ group.groupName }}
              </option>
            </select>
          </label>
          <label>
            Фамилия
            <input v-model.trim="studentForm.firstName" required placeholder="Сидоров" />
          </label>
          <label>
            Имя
            <input v-model.trim="studentForm.middleName" required placeholder="Петр" />
          </label>
          <label>
            Отчество
            <input v-model.trim="studentForm.lastName" placeholder="Петрович" />
          </label>
          <label>
            Статус
            <select v-model="studentForm.status" required>
              <option value="OK">OK</option>
              <option value="NOT_OK">NOT_OK</option>
            </select>
          </label>

          <button type="submit" class="primary-button" :disabled="!selectedGroupId">
            {{ studentForm.id ? 'Сохранить' : 'Создать' }}
          </button>
        </form>

        <section class="panel table-panel">
          <div class="panel-heading">
            <h2>Студенты выбранной группы</h2>
            <span>{{ students.length }}</span>
          </div>

          <div class="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Фамилия</th>
                  <th>Имя</th>
                  <th>Отчество</th>
                  <th>Статус</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="student in students" :key="student.id">
                  <td>{{ student.id }}</td>
                  <td>{{ student.firstName }}</td>
                  <td>{{ student.middleName }}</td>
                  <td>{{ student.lastName || '-' }}</td>
                  <td>{{ student.status }}</td>
                  <td class="actions">
                    <button type="button" @click="editStudent(student)">Править</button>
                    <button type="button" class="danger" @click="deleteStudent(student.id)">Удалить</button>
                  </td>
                </tr>
                <tr v-if="!selectedGroupId">
                  <td colspan="6" class="empty-cell">Сначала выберите группу</td>
                </tr>
                <tr v-else-if="!students.length">
                  <td colspan="6" class="empty-cell">В группе пока нет студентов</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
      </section>

      <section v-show="activeTab === 'lessons'" class="lesson-layout">
        <form class="panel form-panel" @submit.prevent="saveLesson">
          <div class="panel-heading">
            <h2>{{ lessonForm.id ? 'Редактировать урок' : 'Новый урок' }}</h2>
            <button v-if="lessonForm.id" type="button" class="link-button" @click="resetLessonForm">
              Сбросить
            </button>
          </div>

          <label>
            Учитель
            <select v-model.number="lessonForm.teacherId" required>
              <option disabled value="">Выберите учителя</option>
              <option v-for="teacher in teachers" :key="teacher.id" :value="teacher.id">
                {{ teacher.teacherFIO }}
              </option>
            </select>
          </label>
          <label>
            Группа
            <select v-model.number="lessonForm.groupId" required>
              <option disabled value="">Выберите группу</option>
              <option v-for="group in groups" :key="group.id" :value="group.id">
                {{ group.groupName }}
              </option>
            </select>
          </label>
          <label>
            Дисциплина
            <select v-model.number="lessonForm.disciplineId" required>
              <option disabled value="">Выберите дисциплину</option>
              <option v-for="discipline in disciplines" :key="discipline.id" :value="discipline.id">
                {{ disciplineLabel(discipline.name) }}
              </option>
            </select>
          </label>
          <label>
            Дата
            <input v-model="lessonForm.date" type="date" required />
          </label>
          <label>
            Время, час
            <input v-model.number="lessonForm.time" type="number" min="0" max="23" required />
          </label>

          <button type="submit" class="primary-button">
            {{ lessonForm.id ? 'Сохранить' : 'Создать' }}
          </button>
        </form>

        <section class="panel">
          <div class="panel-heading">
            <h2>Поиск уроков</h2>
            <span>{{ lessonPage.totalElements }}</span>
          </div>

          <div class="filter-row">
            <label>
              Искать по
              <select v-model="lessonSearch.kind">
                <option value="teacher">Учителю</option>
                <option value="group">Группе</option>
              </select>
            </label>
            <label v-if="lessonSearch.kind === 'teacher'">
              Учитель
              <select v-model.number="lessonSearch.id" required>
                <option disabled value="">Выберите учителя</option>
                <option v-for="teacher in teachers" :key="teacher.id" :value="teacher.id">
                  {{ teacher.teacherFIO }}
                </option>
              </select>
            </label>
            <label v-else>
              Группа
              <select v-model.number="lessonSearch.id" required>
                <option disabled value="">Выберите группу</option>
                <option v-for="group in groups" :key="group.id" :value="group.id">
                  {{ group.groupName }}
                </option>
              </select>
            </label>
            <label>
              С
              <input v-model="lessonSearch.dateAfter" type="date" required />
            </label>
            <label>
              По
              <input v-model="lessonSearch.dateBefore" type="date" required />
            </label>
          </div>

          <div class="button-row">
            <button type="button" class="primary-button" @click="loadLessons">Найти</button>
            <button type="button" class="danger-button" :disabled="!lessonSearch.id" @click="deleteLessonsBulk">
              Удалить найденный тип
            </button>
          </div>

          <div class="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Дата</th>
                  <th>Время</th>
                  <th>Учитель</th>
                  <th>Группа</th>
                  <th>Дисциплина</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="lesson in lessonPage.items" :key="lesson.id">
                  <td>{{ lesson.id }}</td>
                  <td>{{ lesson.date }}</td>
                  <td>{{ lesson.time }}:00</td>
                  <td>{{ lesson.teacherFIO || nameById(teachers, lesson.teacherId, 'teacherFIO') }}</td>
                  <td>{{ nameById(groups, lesson.groupId, 'groupName') }}</td>
                  <td>{{ disciplineNameById(lesson.disciplineId) }}</td>
                  <td class="actions">
                    <button type="button" @click="editLesson(lesson)">Править</button>
                    <button type="button" class="danger" @click="deleteLesson(lesson.id)">Удалить</button>
                    <button type="button" @click="openVisitEditor(lesson)">Посещения</button>
                  </td>
                </tr>
                <tr v-if="!lessonPage.items.length">
                  <td colspan="7" class="empty-cell">Уроки появятся после поиска</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div class="pagination">
            <button type="button" @click="changeLessonPage(-1)" :disabled="lessonPage.page <= 0">Назад</button>
            <span>Страница {{ lessonPage.page + 1 }} из {{ Math.max(lessonPage.totalPages, 1) }}</span>
            <button
              type="button"
              @click="changeLessonPage(1)"
              :disabled="lessonPage.page + 1 >= lessonPage.totalPages"
            >
              Вперед
            </button>
          </div>
        </section>
      </section>

      <section v-show="activeTab === 'visits'" class="panel-grid">
        <form class="panel form-panel" @submit.prevent="saveVisit">
          <div class="panel-heading">
            <h2>{{ visitForm.id ? 'Редактировать посещение' : 'Посещение урока' }}</h2>
            <button v-if="visitForm.id" type="button" class="link-button" @click="resetVisitForm">
              Сбросить
            </button>
          </div>

          <label>
            ID урока
            <input v-model.number="visitForm.lessonId" type="number" min="1" required @change="loadLessonForVisit" />
          </label>

          <div v-if="visitLesson" class="visit-context">
            <strong>{{ disciplineNameById(visitLesson.disciplineId) }}</strong>
            <span>{{ visitLesson.date }} в {{ visitLesson.time }}:00</span>
            <span>{{ nameById(groups, visitLesson.groupId, 'groupName') }}</span>
          </div>

          <div class="checkbox-list">
            <label v-for="student in visitStudents" :key="student.id" class="check-row">
              <input v-model="visitForm.studentIds" type="checkbox" :value="student.id" />
              <span>{{ studentFio(student) }}</span>
            </label>
            <p v-if="visitForm.lessonId && !visitStudents.length" class="muted">
              Для урока сначала загрузите студентов группы через ID урока.
            </p>
          </div>

          <button type="submit" class="primary-button" :disabled="!visitForm.lessonId || !visitForm.studentIds.length">
            {{ visitForm.id ? 'Сохранить' : 'Создать' }}
          </button>
        </form>

        <section class="panel">
          <div class="panel-heading">
            <h2>Получить посещение</h2>
          </div>
          <div class="filter-row">
            <label>
              ID записи
              <input v-model.number="visitLookupId" type="number" min="1" />
            </label>
            <button type="button" class="primary-button" :disabled="!visitLookupId" @click="loadVisitById">
              Загрузить
            </button>
            <button type="button" class="danger-button" :disabled="!visitLookupId" @click="deleteVisit">
              Удалить
            </button>
          </div>

          <div v-if="visitPreview" class="preview-card">
            <strong>{{ disciplineLabel(visitPreview.name) }}</strong>
            <span v-for="fio in visitPreview.studentsFIO" :key="fio">{{ fio }}</span>
          </div>
          <p v-else class="muted">Backend отдаёт посещение по ID записи. После создания можно загрузить его здесь.</p>
        </section>
      </section>
    </main>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref, watch } from 'vue'
import api from './services/api'

const tabs = [
  { key: 'disciplines', label: 'Дисциплины', icon: '01' },
  { key: 'groups', label: 'Группы', icon: '02' },
  { key: 'students', label: 'Студенты', icon: '03' },
  { key: 'teachers', label: 'Учителя', icon: '04' },
  { key: 'lessons', label: 'Уроки', icon: '05' },
  { key: 'visits', label: 'Посещения', icon: '06' }
]

const disciplineOptions = [
  { value: 'MATH', label: 'Математика' },
  { value: 'ENGLISH', label: 'Английский язык' },
  { value: 'OTHER', label: 'Другое' }
]

const activeTab = ref('disciplines')
const loading = ref(false)
const notice = reactive({ type: '', text: '' })
const session = reactive({ isAuthenticated: false, username: '', role: '' })
const loginForm = reactive({ username: '', password: '' })

const disciplines = ref([])
const groups = ref([])
const teachers = ref([])
const students = ref([])
const visitStudents = ref([])
const visitLesson = ref(null)
const visitPreview = ref(null)
const visitLookupId = ref('')
const selectedGroupId = ref('')
const teacherPage = reactive({ items: [], page: 0, size: 10, totalElements: 0, totalPages: 0 })
const lessonPage = reactive({ items: [], page: 0, size: 10, totalElements: 0, totalPages: 0 })

const disciplineForm = reactive({ id: null, name: '' })
const groupForm = reactive({ id: null, groupName: '' })
const teacherForm = reactive({ id: null, firstName: '', middleName: '', lastName: '' })
const studentForm = reactive({ id: null, firstName: '', middleName: '', lastName: '', status: 'OK' })
const lessonForm = reactive({ id: null, teacherId: '', groupId: '', disciplineId: '', date: '', time: 0 })
const lessonSearch = reactive({ kind: 'teacher', id: '', dateAfter: today(), dateBefore: today() })
const visitForm = reactive({ id: null, lessonId: '', studentIds: [] })

const activeTabMeta = computed(() => tabs.find((tab) => tab.key === activeTab.value) || tabs[0])
const counters = computed(() => ({
  disciplines: disciplines.value.length,
  groups: groups.value.length,
  teachers: teachers.value.length
}))

watch(() => lessonSearch.kind, () => {
  lessonSearch.id = ''
  resetLessonPage()
})

onMounted(async () => {
  syncSession()
  if (session.isAuthenticated) {
    await loadAll()
  }
})

async function login() {
  await withLoading(async () => {
    const auth = await api.auth.login(loginForm)
    applySession(auth)
    loginForm.password = ''
    await loadAll()
    showNotice('success', 'Авторизация успешна')
  })
}

function logout() {
  api.auth.logout()
  applySession(null)
  clearCollections()
  resetForms()
  showNotice('success', 'Сессия завершена')
}

async function loadAll() {
  await withLoading(async () => {
    const [disciplineData, groupData, teacherData] = await Promise.all([
      api.disciplines.getAll(),
      api.groups.getAll(),
      api.teachers.getAll()
    ])
    disciplines.value = disciplineData
    groups.value = groupData
    teachers.value = teacherData
    await loadTeacherPage()
    if (selectedGroupId.value) {
      await fetchStudents()
    }
  })
}

async function saveDiscipline() {
  await runAction(async () => {
    const payload = { name: disciplineForm.name }
    if (disciplineForm.id) {
      await api.disciplines.update(disciplineForm.id, payload)
    } else {
      await api.disciplines.create(payload)
    }
    resetDisciplineForm()
    disciplines.value = await api.disciplines.getAll()
  }, 'Дисциплина сохранена')
}

function editDiscipline(discipline) {
  disciplineForm.id = discipline.id
  disciplineForm.name = discipline.name
}

async function deleteDiscipline(id) {
  if (!window.confirm('Удалить дисциплину?')) return
  await runAction(async () => {
    await api.disciplines.remove(id)
    disciplines.value = await api.disciplines.getAll()
  }, 'Дисциплина удалена')
}

function resetDisciplineForm() {
  disciplineForm.id = null
  disciplineForm.name = ''
}

async function saveGroup() {
  await runAction(async () => {
    const payload = { groupName: groupForm.groupName }
    if (groupForm.id) {
      await api.groups.update(groupForm.id, payload)
    } else {
      await api.groups.create(payload)
    }
    resetGroupForm()
    groups.value = await api.groups.getAll()
  }, 'Группа сохранена')
}

function editGroup(group) {
  groupForm.id = group.id
  groupForm.groupName = group.groupName
}

async function deleteGroup(id) {
  if (!window.confirm('Удалить группу?')) return
  await runAction(async () => {
    await api.groups.remove(id)
    groups.value = await api.groups.getAll()
    if (selectedGroupId.value === id) {
      selectedGroupId.value = ''
      students.value = []
    }
  }, 'Группа удалена')
}

function resetGroupForm() {
  groupForm.id = null
  groupForm.groupName = ''
}

async function saveTeacher() {
  await runAction(async () => {
    const payload = {
      firstName: teacherForm.firstName,
      middleName: teacherForm.middleName,
      lastName: teacherForm.lastName
    }
    if (teacherForm.id) {
      await api.teachers.update(teacherForm.id, payload)
    } else {
      await api.teachers.create(payload)
    }
    resetTeacherForm()
    await reloadTeachers()
  }, 'Учитель сохранён')
}

function editTeacher(teacher) {
  const fio = splitFio(teacher.teacherFIO)
  teacherForm.id = teacher.id
  teacherForm.firstName = fio.firstName
  teacherForm.middleName = fio.middleName
  teacherForm.lastName = fio.lastName
}

async function deleteTeacher(id) {
  if (!window.confirm('Удалить учителя?')) return
  await runAction(async () => {
    await api.teachers.remove(id)
    await reloadTeachers()
  }, 'Учитель удалён')
}

function resetTeacherForm() {
  teacherForm.id = null
  teacherForm.firstName = ''
  teacherForm.middleName = ''
  teacherForm.lastName = ''
}

async function onStudentGroupChange() {
  resetStudentForm()
  await fetchStudents()
}

async function fetchStudents() {
  if (!selectedGroupId.value) {
    students.value = []
    return
  }
  students.value = await api.students.getByGroup(selectedGroupId.value)
}

async function saveStudent() {
  await runAction(async () => {
    const payload = {
      firstName: studentForm.firstName,
      middleName: studentForm.middleName,
      lastName: studentForm.lastName,
      groupId: selectedGroupId.value,
      status: studentForm.status
    }
    if (studentForm.id) {
      await api.students.update({ ...payload, id: studentForm.id })
    } else {
      await api.students.create(payload)
    }
    resetStudentForm()
    await fetchStudents()
  }, 'Студент сохранён')
}

function editStudent(student) {
  studentForm.id = student.id
  studentForm.firstName = student.firstName
  studentForm.middleName = student.middleName
  studentForm.lastName = student.lastName || ''
  studentForm.status = student.status || 'OK'
  selectedGroupId.value = student.groupId || selectedGroupId.value
}

async function deleteStudent(id) {
  if (!window.confirm('Удалить студента?')) return
  await runAction(async () => {
    await api.students.remove(id)
    await fetchStudents()
  }, 'Студент удалён')
}

function resetStudentForm() {
  studentForm.id = null
  studentForm.firstName = ''
  studentForm.middleName = ''
  studentForm.lastName = ''
  studentForm.status = 'OK'
}

async function saveLesson() {
  await runAction(async () => {
    const payload = {
      teacherId: lessonForm.teacherId,
      groupId: lessonForm.groupId,
      disciplineId: lessonForm.disciplineId,
      date: lessonForm.date,
      time: lessonForm.time
    }
    if (lessonForm.id) {
      await api.lessons.update(lessonForm.id, payload)
    } else {
      await api.lessons.create(payload)
    }
    resetLessonForm()
    if (lessonSearch.id) {
      await loadLessons()
    }
  }, 'Урок сохранён')
}

function editLesson(lesson) {
  lessonForm.id = lesson.id
  lessonForm.teacherId = lesson.teacherId
  lessonForm.groupId = lesson.groupId
  lessonForm.disciplineId = lesson.disciplineId
  lessonForm.date = lesson.date
  lessonForm.time = lesson.time
}

function resetLessonForm() {
  lessonForm.id = null
  lessonForm.teacherId = ''
  lessonForm.groupId = ''
  lessonForm.disciplineId = ''
  lessonForm.date = ''
  lessonForm.time = 0
}

async function loadLessons() {
  if (!lessonSearch.id || !lessonSearch.dateAfter || !lessonSearch.dateBefore) {
    showNotice('error', 'Заполните фильтр уроков')
    return
  }
  await runAction(async () => {
    await fetchLessonsPage(0)
  }, 'Уроки загружены')
}

async function deleteLessonsBulk() {
  const text = lessonSearch.kind === 'teacher' ? 'Удалить все уроки учителя?' : 'Удалить все уроки группы?'
  if (!lessonSearch.id || !window.confirm(text)) return
  await runAction(async () => {
    if (lessonSearch.kind === 'teacher') {
      await api.lessons.deleteByTeacher(lessonSearch.id)
    } else {
      await api.lessons.deleteByGroup(lessonSearch.id)
    }
    resetLessonPage()
  }, 'Уроки удалены')
}

async function deleteLesson(id) {
  if (!window.confirm('Удалить урок?')) return
  await runAction(async () => {
    await api.lessons.remove(id)
    if (lessonSearch.id) {
      await fetchLessonsPage(lessonPage.page)
    }
  }, 'Урок удалён')
}

async function openVisitEditor(lesson) {
  activeTab.value = 'visits'
  visitForm.lessonId = lesson.id
  await loadLessonForVisit()
}

async function loadLessonForVisit() {
  if (!visitForm.lessonId) return
  await runAction(async () => {
    visitLesson.value = await api.lessons.getById(visitForm.lessonId)
    visitForm.studentIds = Array.isArray(visitLesson.value.studentIds)
      ? [...visitLesson.value.studentIds]
      : []
    visitStudents.value = await api.students.getByGroup(visitLesson.value.groupId)
  }, 'Данные урока загружены')
}

async function saveVisit() {
  await runAction(async () => {
    const payload = {
      lessonId: visitForm.lessonId,
      studentIds: visitForm.studentIds
    }
    if (visitForm.id) {
      visitPreview.value = await api.lessonVisits.update(visitForm.id, payload)
    } else {
      visitPreview.value = await api.lessonVisits.create(payload)
    }
  }, 'Посещение сохранено')
}

async function loadVisitById() {
  await runAction(async () => {
    visitPreview.value = await api.lessonVisits.getById(visitLookupId.value)
    visitForm.id = visitLookupId.value
  }, 'Посещение загружено')
}

async function deleteVisit() {
  if (!window.confirm('Удалить запись посещения?')) return
  await runAction(async () => {
    await api.lessonVisits.remove(visitLookupId.value)
    resetVisitForm()
    visitPreview.value = null
  }, 'Посещение удалено')
}

function resetVisitForm() {
  visitForm.id = null
  visitForm.lessonId = ''
  visitForm.studentIds = []
  visitLesson.value = null
  visitStudents.value = []
  visitLookupId.value = ''
}

async function withLoading(action) {
  loading.value = true
  try {
    await action()
  } catch (error) {
    handleError(error)
  } finally {
    loading.value = false
  }
}

async function runAction(action, successText) {
  loading.value = true
  clearNotice()
  try {
    await action()
    showNotice('success', successText)
  } catch (error) {
    handleError(error)
  } finally {
    loading.value = false
  }
}

async function reloadTeachers() {
  teachers.value = await api.teachers.getAll()
  await loadTeacherPage(teacherPage.page)
}

async function loadTeacherPage(page = teacherPage.page) {
  const data = await api.teachers.getPage(page, teacherPage.size)
  applyPage(teacherPage, data)
}

async function changeTeacherPage(step) {
  const nextPage = teacherPage.page + step
  if (nextPage < 0 || nextPage >= teacherPage.totalPages) return
  await runAction(async () => {
    await loadTeacherPage(nextPage)
  }, 'Страница учителей обновлена')
}

async function fetchLessonsPage(page = 0) {
  const payload = {
    id: lessonSearch.id,
    dateAfter: lessonSearch.dateAfter,
    dateBefore: lessonSearch.dateBefore
  }
  const data = lessonSearch.kind === 'teacher'
    ? await api.lessons.getByTeacher(payload, page, lessonPage.size)
    : await api.lessons.getByGroup(payload, page, lessonPage.size)
  applyPage(lessonPage, data)
}

async function changeLessonPage(step) {
  if (!lessonSearch.id) return
  const nextPage = lessonPage.page + step
  if (nextPage < 0 || nextPage >= lessonPage.totalPages) return
  await runAction(async () => {
    await fetchLessonsPage(nextPage)
  }, 'Страница уроков обновлена')
}

function disciplineLabel(value) {
  return disciplineOptions.find((item) => item.value === value)?.label || value || '-'
}

function disciplineNameById(id) {
  const discipline = disciplines.value.find((item) => item.id === id)
  return discipline ? disciplineLabel(discipline.name) : id || '-'
}

function nameById(collection, id, field) {
  return collection.find((item) => item.id === id)?.[field] || id || '-'
}

function studentFio(student) {
  return [student.firstName, student.middleName, student.lastName].filter(Boolean).join(' ')
}

function splitFio(value) {
  const parts = String(value || '').trim().split(/\s+/).filter(Boolean)
  return {
    firstName: parts[0] || '',
    middleName: parts[1] || '',
    lastName: parts.slice(2).join(' ')
  }
}

function today() {
  return new Date().toISOString().slice(0, 10)
}

function showNotice(type, text) {
  notice.type = type
  notice.text = text
}

function clearNotice() {
  notice.type = ''
  notice.text = ''
}

function applyPage(target, payload) {
  target.items = Array.isArray(payload?.items) ? payload.items : []
  target.page = Number(payload?.page ?? 0)
  target.size = Number(payload?.size ?? target.size)
  target.totalElements = Number(payload?.totalElements ?? target.items.length)
  target.totalPages = Number(payload?.totalPages ?? (target.items.length ? 1 : 0))
}

function resetLessonPage() {
  lessonPage.items = []
  lessonPage.page = 0
  lessonPage.totalElements = 0
  lessonPage.totalPages = 0
}

function syncSession() {
  applySession(api.auth.getSession())
}

function applySession(auth) {
  session.isAuthenticated = Boolean(auth?.accessToken)
  session.username = auth?.username || ''
  session.role = auth?.role || ''
}

function clearCollections() {
  disciplines.value = []
  groups.value = []
  teachers.value = []
  students.value = []
  visitStudents.value = []
  visitLesson.value = null
  visitPreview.value = null
  selectedGroupId.value = ''
  teacherPage.items = []
  teacherPage.page = 0
  teacherPage.totalElements = 0
  teacherPage.totalPages = 0
  resetLessonPage()
}

function resetForms() {
  resetDisciplineForm()
  resetGroupForm()
  resetTeacherForm()
  resetStudentForm()
  resetLessonForm()
  resetVisitForm()
}

function handleError(error) {
  syncSession()
  showNotice('error', error.message || 'Ошибка запроса')
}

function roleLabel(role) {
  if (role === 'ADMIN') return 'Администратор'
  if (role === 'TEACHER') return 'Преподаватель'
  if (role === 'STUDENT') return 'Студент'
  return role || 'Пользователь'
}
</script>

<style>
:root {
  color: #172026;
  background: #eef2ef;
  font-family: "Manrope", "Avenir Next", "Segoe UI", sans-serif;
  font-size: 16px;
  line-height: 1.5;
}

* {
  box-sizing: border-box;
}

body {
  margin: 0;
  min-width: 320px;
  min-height: 100vh;
  background:
    linear-gradient(135deg, rgba(29, 69, 70, 0.08), transparent 38%),
    linear-gradient(225deg, rgba(188, 95, 48, 0.10), transparent 42%),
    #eef2ef;
}

button,
input,
select {
  font: inherit;
}

button {
  border: 0;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.55;
}

.app-shell {
  display: grid;
  grid-template-columns: 280px minmax(0, 1fr);
  min-height: 100vh;
}

.auth-shell {
  display: grid;
  place-items: center;
  min-height: 100vh;
  padding: 24px;
}

.auth-card {
  width: min(100%, 420px);
  padding: 28px;
  border: 1px solid rgba(23, 32, 38, 0.10);
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.88);
  box-shadow: 0 18px 40px rgba(23, 32, 38, 0.08);
}

.auth-brand {
  margin-bottom: 20px;
}

.auth-form {
  display: grid;
  gap: 14px;
}

.sidebar {
  position: sticky;
  top: 0;
  display: flex;
  flex-direction: column;
  gap: 24px;
  height: 100vh;
  padding: 28px 20px;
  color: #f4f7f3;
  background: #172026;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
}

.brand-mark {
  display: grid;
  place-items: center;
  width: 46px;
  height: 46px;
  border-radius: 8px;
  color: #172026;
  background: #c9e265;
  font-weight: 900;
}

.brand strong,
.brand span {
  display: block;
}

.brand span {
  color: #aeb8b1;
  font-size: 0.9rem;
}

.nav-list {
  display: grid;
  gap: 8px;
}

.nav-list button,
.ghost-button {
  display: flex;
  align-items: center;
  gap: 12px;
  width: 100%;
  padding: 12px 14px;
  border-radius: 8px;
  color: #dce4df;
  background: transparent;
  text-align: left;
}

.nav-list button span {
  color: #82918b;
  font-size: 0.78rem;
  font-weight: 800;
}

.nav-list button.active {
  color: #172026;
  background: #f4f7f3;
}

.ghost-button {
  justify-content: center;
  margin-top: auto;
  border: 1px solid rgba(244, 247, 243, 0.18);
}

.session-card {
  display: grid;
  gap: 4px;
  padding: 12px 14px;
  border: 1px solid rgba(244, 247, 243, 0.18);
  border-radius: 8px;
  color: #f4f7f3;
  background: rgba(244, 247, 243, 0.06);
}

.session-card span {
  color: #aeb8b1;
  font-size: 0.88rem;
}

.workspace {
  width: 100%;
  max-width: 1500px;
  padding: 32px;
}

.page-header {
  display: grid;
  grid-template-columns: minmax(0, 1fr) repeat(3, minmax(120px, 160px));
  gap: 16px;
  align-items: stretch;
  margin-bottom: 22px;
}

.eyebrow {
  margin: 0 0 4px;
  color: #60706a;
  font-size: 0.78rem;
  font-weight: 800;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

h1,
h2 {
  margin: 0;
  line-height: 1.15;
}

h1 {
  font-size: clamp(2rem, 4vw, 3.4rem);
}

h2 {
  font-size: 1.05rem;
}

.status-card,
.panel,
.notice,
.loading-panel {
  border: 1px solid rgba(23, 32, 38, 0.10);
  border-radius: 8px;
  background: rgba(255, 255, 255, 0.78);
  box-shadow: 0 18px 40px rgba(23, 32, 38, 0.08);
}

.status-card {
  display: grid;
  align-content: center;
  padding: 16px;
  color: #60706a;
}

.status-card span {
  display: block;
  color: #172026;
  font-size: 2rem;
  font-weight: 900;
}

.notice {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 18px;
  padding: 14px 16px;
}

.notice.success {
  border-color: rgba(44, 122, 71, 0.35);
  background: #e9f6ed;
}

.notice.error {
  border-color: rgba(176, 55, 55, 0.35);
  background: #fbeaea;
}

.notice button,
.link-button {
  color: #315c70;
  background: transparent;
  font-weight: 800;
}

.loading-panel {
  margin-bottom: 18px;
  padding: 14px 16px;
}

.panel-grid {
  display: grid;
  grid-template-columns: minmax(280px, 380px) minmax(0, 1fr);
  gap: 18px;
}

.lesson-layout {
  display: grid;
  grid-template-columns: minmax(280px, 360px) minmax(0, 1fr);
  gap: 18px;
}

.panel {
  padding: 18px;
}

.panel-heading {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 16px;
}

.panel-heading span {
  color: #60706a;
  font-weight: 800;
}

.form-panel {
  align-self: start;
}

label {
  display: grid;
  gap: 7px;
  margin-bottom: 14px;
  color: #3d4b45;
  font-size: 0.92rem;
  font-weight: 800;
}

input,
select {
  width: 100%;
  min-height: 42px;
  border: 1px solid rgba(23, 32, 38, 0.16);
  border-radius: 8px;
  padding: 10px 12px;
  color: #172026;
  background: #ffffff;
}

input:focus,
select:focus {
  outline: 3px solid rgba(201, 226, 101, 0.55);
  border-color: #6d7c38;
}

.primary-button,
.danger-button {
  min-height: 42px;
  border-radius: 8px;
  padding: 10px 16px;
  color: #172026;
  background: #c9e265;
  font-weight: 900;
}

.danger-button {
  color: #ffffff;
  background: #a33d35;
}

.button-row,
.filter-row {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  align-items: end;
  margin-bottom: 16px;
}

.filter-row label {
  flex: 1 1 170px;
  margin: 0;
}

.table-wrap {
  width: 100%;
  overflow-x: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  min-width: 620px;
}

th,
td {
  border-bottom: 1px solid rgba(23, 32, 38, 0.10);
  padding: 12px 10px;
  text-align: left;
  vertical-align: middle;
}

th {
  color: #60706a;
  font-size: 0.78rem;
  letter-spacing: 0.06em;
  text-transform: uppercase;
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  white-space: nowrap;
}

.actions button {
  border-radius: 8px;
  padding: 7px 10px;
  color: #172026;
  background: #edf1ec;
  font-weight: 800;
}

.actions .danger {
  color: #ffffff;
  background: #a33d35;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 12px;
  margin-top: 16px;
}

.pagination button {
  border-radius: 8px;
  padding: 7px 10px;
  color: #172026;
  background: #edf1ec;
  font-weight: 800;
}

.empty-cell,
.muted {
  color: #60706a;
}

.empty-cell {
  padding: 24px 10px;
  text-align: center;
}

.checkbox-list {
  display: grid;
  gap: 8px;
  max-height: 320px;
  overflow: auto;
  margin-bottom: 16px;
}

.check-row {
  display: flex;
  grid-template-columns: auto 1fr;
  align-items: center;
  gap: 10px;
  margin: 0;
  padding: 10px;
  border-radius: 8px;
  background: #f4f7f3;
}

.check-row input {
  width: 18px;
  min-height: 18px;
}

.visit-context,
.preview-card {
  display: grid;
  gap: 4px;
  margin-bottom: 14px;
  border-radius: 8px;
  padding: 12px;
  background: #f4f7f3;
}

.preview-card span {
  color: #3d4b45;
}

@media (max-width: 980px) {
  .auth-card,
  .app-shell,
  .panel-grid,
  .lesson-layout,
  .page-header {
    grid-template-columns: 1fr;
  }

  .sidebar {
    position: static;
    height: auto;
  }

  .nav-list {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .workspace {
    padding: 20px;
  }
}

@media (max-width: 560px) {
  .nav-list {
    grid-template-columns: 1fr;
  }

  .workspace {
    padding: 16px;
  }

  .actions,
  .notice {
    flex-direction: column;
    align-items: stretch;
  }
}
</style>
