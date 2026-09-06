import { useState, useEffect } from 'react'
import { fetchDashboardStats } from '../api'

export default function DashboardPage() {
  const [stats, setStats] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchDashboardStats()
      .then(setStats)
      .catch(() => setStats(null))
      .finally(() => setLoading(false))
  }, [])

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner" />
        Loading dashboard…
      </div>
    )
  }

  if (!stats) {
    return (
      <div className="empty-state">
        <div className="empty-state-icon">⚠</div>
        <h3>Could not load dashboard</h3>
        <p>Make sure the backend is running on port 8060.</p>
      </div>
    )
  }

  const cards = [
    { label: 'Total Monitors', value: stats.totalMonitors },
    { label: 'Active', value: stats.activeMonitors },
    { label: 'Favorites', value: stats.favoriteMonitors },
    { label: 'Checks Performed', value: stats.totalChecksPerformed },
    { label: 'Unread Notifications', value: stats.unreadNotifications },
  ]

  return (
    <>
      <div className="page-header">
        <div>
          <h1>Dashboard</h1>
          <p className="page-header-subtitle">Overview of your monitoring activity</p>
        </div>
      </div>
      <div className="stats-grid">
        {cards.map((c) => (
          <div className="stat-card" key={c.label}>
            <div className="stat-label">{c.label}</div>
            <div className="stat-value">{c.value}</div>
          </div>
        ))}
      </div>
    </>
  )
}
