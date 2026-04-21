import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { Link } from 'react-router-dom'
import { saveTrainingOffering } from '../services/directoryService'

const inputStyle = {
  width: '100%',
  padding: '11px 14px',
  background: 'rgba(0,210,255,0.04)',
  border: '1px solid rgba(0,210,255,0.15)',
  borderRadius: '8px',
  color: '#ffffff',
  fontFamily: 'Space Grotesk, sans-serif',
  fontSize: '14px',
  outline: 'none',
  boxSizing: 'border-box',
  marginBottom: '12px',
}

export default function TrainingProviderDashboard() {
  const { user } = useAuth()
  const [form, setForm] = useState({ programName: '', skillsCovered: '', format: '', contactInfo: '' })
  const [submitted, setSubmitted] = useState(false)
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    await saveTrainingOffering(form)
    setLoading(false)
    setSubmitted(true)
    setForm({ programName: '', skillsCovered: '', format: '', contactInfo: '' })
    setTimeout(() => setSubmitted(false), 3000)
  }

  return (
    <div style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
      <div style={{ marginBottom: '2rem' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '28px', fontWeight: 800, color: '#fff', margin: '0 0 6px' }}>
          Welcome, <span style={{ color: '#00d2ff' }}>{user.name}.</span>
        </h1>
        <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '14px', color: 'rgba(255,255,255,0.4)', margin: 0, letterSpacing: '0.06em' }}>
          TRAINING PROVIDER DASHBOARD
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1rem', marginBottom: '2rem' }}>
        {[
          { label: 'Active Programs', value: '2', color: '#00d2ff' },
          { label: 'Enrolled Learners', value: '47', color: '#c472f0' },
          { label: 'Skills Covered', value: '18', color: '#00ffcc' },
        ].map(stat => (
          <div key={stat.label} style={{
            background: 'rgba(0,210,255,0.03)',
            border: '1px solid rgba(0,210,255,0.1)',
            borderRadius: '12px',
            padding: '1.25rem',
          }}>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '11px', color: 'rgba(255,255,255,0.4)', letterSpacing: '0.1em', textTransform: 'uppercase', margin: '0 0 8px' }}>{stat.label}</p>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '32px', fontWeight: 800, color: stat.color, margin: 0 }}>{stat.value}</p>
          </div>
        ))}
      </div>

      {/* Post Training Program Form */}
      <div style={{
        background: 'rgba(0,210,255,0.03)',
        border: '1px solid rgba(0,210,255,0.12)',
        borderRadius: '12px',
        padding: '1.5rem',
        marginBottom: '1rem',
      }}>
        <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00d2ff', margin: '0 0 16px' }}>
          Post a Training Program
        </p>
        <form onSubmit={handleSubmit}>
          <input
            style={inputStyle}
            name="programName"
            placeholder="Program name"
            value={form.programName}
            onChange={handleChange}
            required
          />
          <input
            style={inputStyle}
            name="skillsCovered"
            placeholder="Skills covered (e.g. Java, React, SQL)"
            value={form.skillsCovered}
            onChange={handleChange}
            required
          />
          <input
            style={inputStyle}
            name="format"
            placeholder="Format (e.g. In-Person, Online, Hybrid)"
            value={form.format}
            onChange={handleChange}
            required
          />
          <input
            style={{ ...inputStyle, marginBottom: '16px' }}
            name="contactInfo"
            placeholder="Contact info (email or phone)"
            value={form.contactInfo}
            onChange={handleChange}
            required
          />
          <button
            type="submit"
            disabled={loading}
            style={{
              padding: '11px 28px',
              background: 'rgba(0,210,255,0.1)',
              border: '1px solid rgba(0,210,255,0.4)',
              borderRadius: '8px',
              color: '#00d2ff',
              fontFamily: 'Space Grotesk, sans-serif',
              fontSize: '14px',
              fontWeight: 600,
              cursor: loading ? 'not-allowed' : 'pointer',
              letterSpacing: '0.06em',
              opacity: loading ? 0.6 : 1,
            }}
          >
            {loading ? 'Posting...' : 'Post Program →'}
          </button>
          {submitted && (
            <span style={{ marginLeft: '16px', fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: '#00ffcc' }}>
              ✓ Program posted successfully
            </span>
          )}
        </form>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
        <Link to="/directory" style={{ textDecoration: 'none' }}>
          <div style={{ background: 'rgba(0,210,255,0.04)', border: '1px solid rgba(0,210,255,0.15)', borderRadius: '12px', padding: '1.5rem' }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00d2ff', margin: '0 0 8px' }}>Manage Programs →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Update your training offerings in the directory</p>
          </div>
        </Link>
        <Link to="/search" style={{ textDecoration: 'none' }}>
          <div style={{ background: 'rgba(196,114,240,0.04)', border: '1px solid rgba(196,114,240,0.15)', borderRadius: '12px', padding: '1.5rem' }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#c472f0', margin: '0 0 8px' }}>Market Intelligence →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>See what skills employers are hiring for right now</p>
          </div>
        </Link>
      </div>
    </div>
  )
}
