const BASE = '/api';

function getAuthHeader() {
  const token = localStorage.getItem('token');
  return token ? { 'Authorization': `Bearer ${token}` } : {};
}

async function request(url, options = {}) {
  const res = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...getAuthHeader(), ...options.headers },
    ...options,
  });
  if (!res.ok) {
    let errorMsg = `Request failed: ${res.status}`;
    try {
      const errData = await res.json();
      if (errData && errData.message) {
        errorMsg = errData.message;
      }
    } catch {
      const text = await res.text().catch(() => null);
      if (text) errorMsg = text;
    }
    throw new Error(errorMsg);
  }
  if (res.status === 204) return null;
  return res.json();
}

// Auth
export function login(username, password) {
  return request(`${BASE}/auth/login`, {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  });
}

export function register(username, email, password) {
  return request(`${BASE}/auth/register`, {
    method: 'POST',
    body: JSON.stringify({ username, email, password }),
  });
}

// Dashboard
export function fetchDashboardStats() {
  return request(`${BASE}/users/me/dashboard/stats`);
}

// Monitors
export function fetchMonitors() {
  return request(`${BASE}/users/me/monitors`);
}

export function fetchMonitor(id) {
  return request(`${BASE}/users/me/monitors/${id}`);
}

export function createMonitor(data) {
  return request(`${BASE}/users/me/monitors`, {
    method: 'POST',
    body: JSON.stringify(data),
  });
}

export function deleteMonitor(id) {
  return request(`${BASE}/users/me/monitors/${id}`, { method: 'DELETE' });
}

export function toggleFavorite(id) {
  return request(`${BASE}/users/me/monitors/${id}/favorite`, { method: 'PATCH' });
}

export function updateMonitorStatus(id, status) {
  return request(`${BASE}/users/me/monitors/${id}/status?status=${status}`, {
    method: 'PATCH',
  });
}

export function runCheckNow(id) {
  return request(`${BASE}/users/me/monitors/${id}/check`, { method: 'POST' });
}

// Notifications
export function fetchNotifications(unreadOnly = false) {
  return request(`${BASE}/users/me/notifications?unreadOnly=${unreadOnly}`);
}

export function fetchUnreadCount() {
  return request(`${BASE}/users/me/notifications/unread-count`);
}

export function markNotificationRead(id) {
  return request(`${BASE}/users/me/notifications/${id}/read`, { method: 'PATCH' });
}
