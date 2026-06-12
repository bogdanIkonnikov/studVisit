const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'
const AUTH_STORAGE_KEY = 'studvisit-auth'

function getStoredAuth() {
  try {
    const raw = window.localStorage.getItem(AUTH_STORAGE_KEY)
    return raw ? JSON.parse(raw) : null
  } catch {
    return null
  }
}

function setStoredAuth(auth) {
  window.localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(auth))
}

function clearStoredAuth() {
  window.localStorage.removeItem(AUTH_STORAGE_KEY)
}

function buildUrl(path, query) {
  const url = new URL(`${API_BASE_URL}${path}`, window.location.origin)
  Object.entries(query || {}).forEach(([key, value]) => {
    if (value !== undefined && value !== null && value !== '') {
      url.searchParams.set(key, value)
    }
  })
  return `${url.pathname}${url.search}`
}

async function request(path, options = {}) {
  const auth = getStoredAuth()
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {})
  }

  if (auth?.accessToken) {
    headers.Authorization = `${auth.tokenType || 'Bearer'} ${auth.accessToken}`
  }

  const response = await fetch(buildUrl(path, options.query), {
    method: options.method || 'GET',
    headers,
    body: options.body ? JSON.stringify(options.body) : undefined
  })

  const payload = await readJson(response)

  if (response.status === 401 && !options.skipRefresh && auth?.refreshToken && !path.startsWith('/auth/')) {
    const refreshed = await refreshTokens(auth.refreshToken)
    if (refreshed) {
      return request(path, { ...options, skipRefresh: true })
    }
  }

  if (!response.ok || payload?.success === false) {
    if (response.status === 401) {
      clearStoredAuth()
    }
    throw new Error(getErrorMessage(payload, response.status))
  }

  return payload?.data ?? null
}

async function refreshTokens(refreshToken) {
  try {
    const payload = await request('/auth/refresh', {
      method: 'POST',
      body: { refreshToken },
      skipRefresh: true
    })
    persistAuth(payload)
    return true
  } catch {
    clearStoredAuth()
    return false
  }
}

function persistAuth(payload) {
  const auth = {
    accessToken: payload.accessToken,
    refreshToken: payload.refreshToken,
    tokenType: payload.tokenType,
    username: payload.username,
    role: payload.role,
    accessExpiresAt: payload.accessExpiresAt,
    refreshExpiresAt: payload.refreshExpiresAt
  }
  setStoredAuth(auth)
  return auth
}

async function readJson(response) {
  const text = await response.text()
  if (!text) {
    return null
  }

  try {
    return JSON.parse(text)
  } catch {
    throw new Error(`Backend returned non-JSON response (${response.status})`)
  }
}

function getErrorMessage(payload, status) {
  const details = Array.isArray(payload?.details) ? payload.details.join(', ') : ''
  const message = payload?.errorMessage || payload?.message || `HTTP ${status}`
  return details ? `${message}: ${details}` : message
}

const api = {
  auth: {
    login: async (body) => {
      const payload = await request('/auth/login', { method: 'POST', body, skipRefresh: true })
      return persistAuth(payload)
    },
    logout: () => clearStoredAuth(),
    getSession: () => getStoredAuth(),
    hasSession: () => Boolean(getStoredAuth()?.accessToken)
  },
  disciplines: {
    getAll: () => request('/disciplines'),
    getById: (id) => request(`/disciplines/${id}`),
    create: (body) => request('/disciplines/add', { method: 'POST', body }),
    update: (id, body) => request(`/disciplines/${id}/edit`, { method: 'PUT', body }),
    remove: (id) => request(`/disciplines/${id}/delete`, { method: 'DELETE' })
  },
  groups: {
    getAll: () => request('/groups/getAll'),
    getById: (id) => request(`/groups/${id}`),
    create: (body) => request('/groups/add', { method: 'POST', body }),
    update: (id, body) => request(`/groups/${id}/edit`, { method: 'PUT', body }),
    remove: (id) => request(`/groups/${id}/delete`, { method: 'DELETE' })
  },
  teachers: {
    getAll: () => request('/teachers'),
    getPage: (page, size) => request('/teachers', { query: { page, size } }),
    getById: (id) => request(`/teachers/${id}`),
    create: (body) => request('/teachers/add', { method: 'POST', body }),
    update: (id, body) => request(`/teachers/${id}/edit`, { method: 'PUT', body }),
    remove: (id) => request(`/teachers/${id}/delete`, { method: 'DELETE' })
  },
  students: {
    getById: (id) => request(`/students/getStudentById/${id}`),
    getByGroup: (id) => request(`/students/getStudentByGroupId/${id}`),
    create: (body) => request('/students/addStudent', { method: 'POST', body }),
    update: (body) => request('/students/editStudent', { method: 'PUT', body }),
    remove: (id) => request(`/students/${id}/delete`, { method: 'DELETE' })
  },
  lessons: {
    getById: (id) => request(`/lessons/${id}`),
    getByTeacher: (body, page, size) => request('/lessons/all/by-teacher', { method: 'POST', body, query: { page, size } }),
    getByGroup: (body, page, size) => request('/lessons/all/by-group', { method: 'POST', body, query: { page, size } }),
    create: (body) => request('/lessons/add', { method: 'POST', body }),
    update: (id, body) => request(`/lessons/${id}/edit`, { method: 'PUT', body }),
    remove: (id) => request(`/lessons/${id}/delete`, { method: 'DELETE' }),
    deleteByTeacher: (id) => request(`/lessons/delete/teacher/${id}`, { method: 'DELETE' }),
    deleteByGroup: (id) => request(`/lessons/delete/group/${id}`, { method: 'DELETE' })
  },
  lessonVisits: {
    getById: (id) => request(`/lesson-visits/${id}`),
    create: (body) => request('/lesson-visits/add', { method: 'POST', body }),
    update: (id, body) => request(`/lesson-visits/${id}/edit`, { method: 'PUT', body }),
    remove: (id) => request(`/lesson-visits/${id}/delete`, { method: 'DELETE' })
  }
}

export default api
