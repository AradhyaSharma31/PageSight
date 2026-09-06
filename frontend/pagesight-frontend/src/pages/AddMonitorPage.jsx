import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { createMonitor } from '../api'

export default function AddMonitorPage() {
  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [url, setUrl] = useState('')
  const [interval, setInterval] = useState(30)
  const [elements, setElements] = useState([])
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)

  const addElement = () => {
    setElements([...elements, { selectorPath: '', label: '' }])
  }

  const updateElement = (index, field, value) => {
    setElements(elements.map((el, i) => (i === index ? { ...el, [field]: value } : el)))
  }

  const removeElement = (index) => {
    setElements(elements.filter((_, i) => i !== index))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    setSubmitting(true)

    try {
      const data = {
        name,
        url,
        checkIntervalMinutes: parseInt(interval, 10),
        elements: elements.filter((el) => el.selectorPath.trim()),
      }
      await createMonitor(data)
      navigate('/monitors')
    } catch (err) {
      setError(err.message || 'Failed to create monitor')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <>
      <div className="page-header">
        <div>
          <h1>Add Monitor</h1>
          <p className="page-header-subtitle">Set up a new website monitor</p>
        </div>
      </div>

      <div className="card" style={{ maxWidth: 600 }}>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label" htmlFor="monitor-name">Name</label>
            <input
              id="monitor-name"
              className="form-input"
              type="text"
              placeholder="e.g. Product Price Tracker"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="monitor-url">URL</label>
            <input
              id="monitor-url"
              className="form-input"
              type="url"
              placeholder="https://example.com/page"
              value={url}
              onChange={(e) => setUrl(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="monitor-interval">Check Interval (minutes)</label>
            <input
              id="monitor-interval"
              className="form-input"
              type="number"
              min="1"
              value={interval}
              onChange={(e) => setInterval(e.target.value)}
              required
            />
            <p className="form-hint">How often PageSight should check for changes.</p>
          </div>

          <div className="form-group">
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: 10 }}>
              <label className="form-label" style={{ marginBottom: 0 }}>Element Selectors</label>
              <button type="button" className="btn btn-secondary btn-sm" onClick={addElement}>
                + Add Element
              </button>
            </div>

            {elements.length === 0 && (
              <p className="form-hint">No element selectors added. The entire page will be monitored.</p>
            )}

            {elements.map((el, i) => (
              <div key={i} style={{ display: 'flex', gap: 8, marginBottom: 8, alignItems: 'flex-start' }}>
                <div style={{ flex: 1 }}>
                  <input
                    className="form-input"
                    type="text"
                    placeholder="CSS selector (e.g. #price, .title)"
                    value={el.selectorPath}
                    onChange={(e) => updateElement(i, 'selectorPath', e.target.value)}
                  />
                </div>
                <div style={{ flex: 1 }}>
                  <input
                    className="form-input"
                    type="text"
                    placeholder="Label (optional)"
                    value={el.label}
                    onChange={(e) => updateElement(i, 'label', e.target.value)}
                  />
                </div>
                <button
                  type="button"
                  className="btn btn-danger btn-sm"
                  onClick={() => removeElement(i)}
                  style={{ marginTop: 2 }}
                >
                  ✕
                </button>
              </div>
            ))}
          </div>

          {error && (
            <div style={{ color: 'var(--color-error)', fontSize: '0.85rem', marginBottom: 16 }}>
              {error}
            </div>
          )}

          <div style={{ display: 'flex', gap: 8 }}>
            <button type="submit" className="btn btn-primary" disabled={submitting}>
              {submitting ? 'Creating…' : 'Create Monitor'}
            </button>
            <button type="button" className="btn btn-secondary" onClick={() => navigate('/monitors')}>
              Cancel
            </button>
          </div>
        </form>
      </div>
    </>
  )
}
