import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import {
  fetchMonitors,
  toggleFavorite,
  updateMonitorStatus,
  deleteMonitor,
  runCheckNow,
} from '../api'

export default function MonitorsPage() {
  const [monitors, setMonitors] = useState([])
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  const load = () => {
    setLoading(true)
    fetchMonitors()
      .then(setMonitors)
      .catch(() => setMonitors([]))
      .finally(() => setLoading(false))
  }

  useEffect(load, [])

  const handleFavorite = async (id) => {
    const updated = await toggleFavorite(id)
    setMonitors((prev) => prev.map((m) => (m.id === id ? updated : m)))
  }

  const handleToggleStatus = async (monitor) => {
    const newStatus = monitor.status === 'ACTIVE' ? 'PAUSED' : 'ACTIVE'
    const updated = await updateMonitorStatus(monitor.id, newStatus)
    setMonitors((prev) => prev.map((m) => (m.id === monitor.id ? updated : m)))
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this monitor?')) return
    await deleteMonitor(id)
    setMonitors((prev) => prev.filter((m) => m.id !== id))
  }

  const handleCheck = async (id) => {
    await runCheckNow(id)
    load()
  }

  const formatDate = (d) => {
    if (!d) return '—'
    return new Date(d).toLocaleString(undefined, {
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    })
  }

  const statusBadge = (status) => {
    const cls =
      status === 'ACTIVE'
        ? 'badge-active'
        : status === 'PAUSED'
          ? 'badge-paused'
          : 'badge-error'
    return (
      <span className={`badge ${cls}`}>
        <span className="badge-dot" />
        {status.charAt(0) + status.slice(1).toLowerCase()}
      </span>
    )
  }

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner" />
        Loading monitors…
      </div>
    )
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1>Monitors</h1>
          <p className="page-header-subtitle">{monitors.length} monitor{monitors.length !== 1 ? 's' : ''} configured</p>
        </div>
        <button className="btn btn-primary" onClick={() => navigate('/monitors/new')}>
          <svg className="btn-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <line x1="12" y1="5" x2="12" y2="19" />
            <line x1="5" y1="12" x2="19" y2="12" />
          </svg>
          Add Monitor
        </button>
      </div>

      {monitors.length === 0 ? (
        <div className="card">
          <div className="empty-state">
            <div className="empty-state-icon">📡</div>
            <h3>No monitors yet</h3>
            <p>Create your first monitor to start tracking changes.</p>
          </div>
        </div>
      ) : (
        <div className="card" style={{ padding: 0, overflow: 'hidden' }}>
          <table className="data-table">
            <thead>
              <tr>
                <th style={{ width: 40 }}></th>
                <th>Name</th>
                <th>URL</th>
                <th>Status</th>
                <th>Interval</th>
                <th>Last Checked</th>
                <th style={{ width: 140 }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {monitors.map((m) => (
                <tr key={m.id}>
                  <td>
                    <button
                      className={`fav-btn ${m.favorite ? 'active' : ''}`}
                      onClick={() => handleFavorite(m.id)}
                      title={m.favorite ? 'Remove from favorites' : 'Add to favorites'}
                    >
                      {m.favorite ? '★' : '☆'}
                    </button>
                  </td>
                  <td className="monitor-name">
                    <Link to={`/monitors/${m.id}`}>{m.name}</Link>
                  </td>
                  <td className="url-cell" title={m.url}>{m.url}</td>
                  <td>{statusBadge(m.status)}</td>
                  <td style={{ color: 'var(--color-text-secondary)' }}>{m.checkIntervalMinutes}m</td>
                  <td style={{ color: 'var(--color-text-tertiary)', fontSize: '0.82rem' }}>
                    {formatDate(m.lastCheckedAt)}
                  </td>
                  <td>
                    <div className="actions-row">
                      <button
                        className="btn btn-ghost btn-sm"
                        onClick={() => handleToggleStatus(m)}
                        title={m.status === 'ACTIVE' ? 'Pause' : 'Resume'}
                      >
                        {m.status === 'ACTIVE' ? '⏸' : '▶'}
                      </button>
                      <button
                        className="btn btn-ghost btn-sm"
                        onClick={() => handleCheck(m.id)}
                        title="Check now"
                      >
                        ↻
                      </button>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => handleDelete(m.id)}
                        title="Delete"
                      >
                        ✕
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </>
  )
}
