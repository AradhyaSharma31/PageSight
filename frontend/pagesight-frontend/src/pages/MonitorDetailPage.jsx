import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import {
  fetchMonitor,
  toggleFavorite,
  updateMonitorStatus,
  deleteMonitor,
  runCheckNow,
} from '../api'

export default function MonitorDetailPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [monitor, setMonitor] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchMonitor(id)
      .then(setMonitor)
      .catch(() => setMonitor(null))
      .finally(() => setLoading(false))
  }, [id])

  const handleFavorite = async () => {
    const updated = await toggleFavorite(id)
    setMonitor(updated)
  }

  const handleToggleStatus = async () => {
    const newStatus = monitor.status === 'ACTIVE' ? 'PAUSED' : 'ACTIVE'
    const updated = await updateMonitorStatus(id, newStatus)
    setMonitor(updated)
  }

  const handleDelete = async () => {
    if (!window.confirm('Delete this monitor?')) return
    await deleteMonitor(id)
    navigate('/monitors')
  }

  const handleCheck = async () => {
    await runCheckNow(id)
    const updated = await fetchMonitor(id)
    setMonitor(updated)
  }

  const formatDate = (d) => {
    if (!d) return '—'
    return new Date(d).toLocaleString(undefined, {
      year: 'numeric',
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
        Loading monitor…
      </div>
    )
  }

  if (!monitor) {
    return (
      <div className="empty-state">
        <div className="empty-state-icon">⚠</div>
        <h3>Monitor not found</h3>
        <p>This monitor may have been deleted.</p>
      </div>
    )
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1>{monitor.name}</h1>
          <p className="page-header-subtitle">Monitor details and tracked elements</p>
        </div>
        <div className="actions-row">
          <button
            className={`fav-btn ${monitor.favorite ? 'active' : ''}`}
            onClick={handleFavorite}
            title={monitor.favorite ? 'Unfavorite' : 'Favorite'}
            style={{ fontSize: '1.3rem' }}
          >
            {monitor.favorite ? '★' : '☆'}
          </button>
          <button className="btn btn-secondary btn-sm" onClick={handleToggleStatus}>
            {monitor.status === 'ACTIVE' ? '⏸ Pause' : '▶ Resume'}
          </button>
          <button className="btn btn-secondary btn-sm" onClick={handleCheck}>
            ↻ Check Now
          </button>
          <button className="btn btn-danger btn-sm" onClick={handleDelete}>
            ✕ Delete
          </button>
          <button className="btn btn-ghost btn-sm" onClick={() => navigate('/monitors')}>
            ← Back
          </button>
        </div>
      </div>

      <div className="card" style={{ marginBottom: 20 }}>
        <div className="detail-grid">
          <div className="detail-item">
            <div className="detail-label">URL</div>
            <div className="detail-value">
              <a href={monitor.url} target="_blank" rel="noopener noreferrer">
                {monitor.url}
              </a>
            </div>
          </div>
          <div className="detail-item">
            <div className="detail-label">Status</div>
            <div className="detail-value">{statusBadge(monitor.status)}</div>
          </div>
          <div className="detail-item">
            <div className="detail-label">Check Interval</div>
            <div className="detail-value">{monitor.checkIntervalMinutes} minutes</div>
          </div>
          <div className="detail-item">
            <div className="detail-label">Last Checked</div>
            <div className="detail-value">{formatDate(monitor.lastCheckedAt)}</div>
          </div>
          <div className="detail-item">
            <div className="detail-label">Created</div>
            <div className="detail-value">{formatDate(monitor.createdAt)}</div>
          </div>
          <div className="detail-item">
            <div className="detail-label">Elements Tracked</div>
            <div className="detail-value">{monitor.elements?.length || 0}</div>
          </div>
        </div>
      </div>

      <div className="card">
        <h3 style={{ marginBottom: 14 }}>Element Selectors</h3>
        {(!monitor.elements || monitor.elements.length === 0) ? (
          <div className="empty-state" style={{ padding: '24px 0' }}>
            <p>No element selectors configured. Monitoring the entire page.</p>
          </div>
        ) : (
          <div className="selector-list">
            {monitor.elements.map((el) => (
              <div className="selector-item" key={el.id}>
                <div className="selector-item-info">
                  <span className="selector-label">{el.label || 'Unlabeled'}</span>
                  <span className="selector-path">{el.selectorPath}</span>
                </div>
                <span style={{ fontSize: '0.78rem', color: 'var(--color-text-tertiary)' }}>
                  Checked: {formatDate(el.lastCheckedAt)}
                </span>
              </div>
            ))}
          </div>
        )}
      </div>
    </>
  )
}
