import { useState, useEffect } from 'react'
import { fetchNotifications, markNotificationRead } from '../api'

export default function NotificationsPage() {
  const [notifications, setNotifications] = useState([])
  const [loading, setLoading] = useState(true)
  const [filter, setFilter] = useState('all')

  const load = (unreadOnly) => {
    setLoading(true)
    fetchNotifications(unreadOnly)
      .then(setNotifications)
      .catch(() => setNotifications([]))
      .finally(() => setLoading(false))
  }

  useEffect(() => {
    load(filter === 'unread')
  }, [filter])

  const handleMarkRead = async (id) => {
    await markNotificationRead(id)
    setNotifications((prev) =>
      prev.map((n) => (n.id === id ? { ...n, read: true } : n))
    )
  }

  const formatDate = (d) => {
    if (!d) return ''
    return new Date(d).toLocaleString(undefined, {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    })
  }

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner" />
        Loading notifications…
      </div>
    )
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1>Notifications</h1>
          <p className="page-header-subtitle">Change alerts from your monitors</p>
        </div>
      </div>

      <div className="filter-row">
        <button
          className={`filter-btn ${filter === 'all' ? 'active' : ''}`}
          onClick={() => setFilter('all')}
        >
          All
        </button>
        <button
          className={`filter-btn ${filter === 'unread' ? 'active' : ''}`}
          onClick={() => setFilter('unread')}
        >
          Unread
        </button>
      </div>

      <div className="card" style={{ padding: 0, overflow: 'hidden' }}>
        {notifications.length === 0 ? (
          <div className="empty-state">
            <div className="empty-state-icon">🔔</div>
            <h3>No notifications</h3>
            <p>
              {filter === 'unread'
                ? 'All caught up — no unread notifications.'
                : 'Notifications will appear here when monitored content changes.'}
            </p>
          </div>
        ) : (
          <div className="notification-list">
            {notifications.map((n) => (
              <div
                className={`notification-item ${!n.read ? 'unread' : ''}`}
                key={n.id}
              >
                <div className={`notification-dot ${n.read ? 'read' : ''}`} />
                <div className="notification-body">
                  <div className="notification-message">{n.message}</div>
                  <div className="notification-meta">
                    {n.monitorName} · {formatDate(n.createdAt)}
                  </div>
                </div>
                <div className="notification-actions">
                  {!n.read && (
                    <button
                      className="btn btn-ghost btn-sm"
                      onClick={() => handleMarkRead(n.id)}
                    >
                      Mark read
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </>
  )
}
