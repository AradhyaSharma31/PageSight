import { useState, useEffect } from 'react'
import { Routes, Route, NavLink, useLocation, Navigate, Outlet } from 'react-router-dom'
import { fetchUnreadCount } from './api'
import { AuthProvider, useAuth } from './AuthContext'
import DashboardPage from './pages/DashboardPage'
import MonitorsPage from './pages/MonitorsPage'
import AddMonitorPage from './pages/AddMonitorPage'
import MonitorDetailPage from './pages/MonitorDetailPage'
import NotificationsPage from './pages/NotificationsPage'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import './App.css'

function ProtectedRoute() {
  const { isAuthenticated, loading } = useAuth()

  if (loading) {
    return <div className="loading">Loading...</div>
  }

  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />
}

function PublicRoute() {
  const { isAuthenticated, loading } = useAuth()

  if (loading) {
    return <div className="loading">Loading...</div>
  }

  return isAuthenticated ? <Navigate to="/" replace /> : <Outlet />
}

function AppLayout() {
  const [unreadCount, setUnreadCount] = useState(0)
  const location = useLocation()
  const { logout, user } = useAuth()

  useEffect(() => {
    fetchUnreadCount()
      .then(setUnreadCount)
      .catch(() => setUnreadCount(0))
  }, [location.pathname])

  return (
    <div className="app-layout">
      <aside className="sidebar">
        <div className="sidebar-brand">
          <h1>
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
              <circle cx="12" cy="12" r="3" />
            </svg>
            Page<span>Sight</span>
          </h1>
        </div>
        <nav className="sidebar-nav">
          <NavLink to="/" end>
            <svg className="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <rect x="3" y="3" width="7" height="7" />
              <rect x="14" y="3" width="7" height="7" />
              <rect x="3" y="14" width="7" height="7" />
              <rect x="14" y="14" width="7" height="7" />
            </svg>
            Dashboard
          </NavLink>
          <NavLink to="/monitors">
            <svg className="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83" />
            </svg>
            Monitors
          </NavLink>
          <NavLink to="/notifications">
            <svg className="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9" />
              <path d="M13.73 21a2 2 0 0 1-3.46 0" />
            </svg>
            Notifications
            {unreadCount > 0 && <span className="nav-badge">{unreadCount}</span>}
          </NavLink>
        </nav>
        <div className="sidebar-user">
          <span>{user?.username}</span>
          <button onClick={logout} className="btn btn-secondary btn-sm">Logout</button>
        </div>
      </aside>

      <main className="main-content">
        <Outlet />
      </main>
    </div>
  )
}

function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route element={<PublicRoute />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
        </Route>
        <Route element={<ProtectedRoute />}>
          <Route element={<AppLayout />}>
            <Route path="/" element={<DashboardPage />} />
            <Route path="/monitors" element={<MonitorsPage />} />
            <Route path="/monitors/new" element={<AddMonitorPage />} />
            <Route path="/monitors/:id" element={<MonitorDetailPage />} />
            <Route path="/notifications" element={<NotificationsPage />} />
          </Route>
        </Route>
      </Routes>
    </AuthProvider>
  )
}

export default App
