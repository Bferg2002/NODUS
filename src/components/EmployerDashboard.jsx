import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { Link } from 'react-router-dom'
import { saveEmployerPosting } from '../services/directoryService'

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

export default function EmployerDashboard() {
  const { user } = useAuth()
  const [form, setForm] = useState({ roleTitle: '', skillsNeeded: '', companyName: '', location: '' })
  const [submitted, setSubmitted] = useState(false)
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    await saveEmployerPosting(form)
    setLoading(false)
    setSubmitted(true)
    setForm({ roleTitle: '', skillsNeeded: '', companyName: '', location: '' })
    setTimeout(() => setSubmitted(false), 3000)
  }

  return (
    <div style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
      <div style={{ marginBottom: '2rem' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '28px', fontWeight: 800, color: '#fff', margin: '0 0 6px' }}>
          Welcome, <span style={{ color: '#00d2ff' }}>{user.name}.</span>
        </h1>
        <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '14px', color: 'rgba(255,255,255,0.4)', margin: 0, letterSpacing: '0.06em' }}>
          EMPLOYER INTELLIGENCE DASHBOARD
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1rem', marginBottom: '2rem' }}>
        {[
          { label: 'Active Postings', value: '4', color: '#00d2ff' },
          { label: 'Talent Matches', value: '23', color: '#00ffcc' },
          { label: 'Applications', value: '11', color: '#c472f0' },
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

      {/* Post Job Opening Form */}
      <div style={{
        background: 'rgba(0,210,255,0.03)',
        border: '1px solid rgba(0,210,255,0.12)',
        borderRadius: '12px',
        padding: '1.5rem',
        marginBottom: '1rem',
      }}>
        <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00d2ff', margin: '0 0 16px' }}>
          Post a Job Opening
        </p>
        <form onSubmit={handleSubmit}>
          <input
            style={inputStyle}
            name="roleTitle"
            placeholder="Role title (e.g. Junior Backend Engineer)"
            value={form.roleTitle}
            onChange={handleChange}
            required
          />
          <input
            style={inputStyle}
            name="skillsNeeded"
            placeholder="Skills needed (e.g. Java, Spring Boot, SQL)"
            value={form.skillsNeeded}
            onChange={handleChange}
            required
          />
          <input
            style={inputStyle}
            name="companyName"
            placeholder="Company name"
            value={form.companyName}
            onChange={handleChange}
            required
          />
          <input
            style={{ ...inputStyle, marginBottom: '16px' }}
            name="location"
            placeholder="Location (e.g. Wilmington, DE)"
            value={form.location}
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
            {loading ? 'Posting...' : 'Post Opening →'}
          </button>
          {submitted && (
            <span style={{ marginLeft: '16px', fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: '#00ffcc' }}>
              ✓ Job opening posted successfully
            </span>
          )}
        </form>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
        <Link to="/directory" style={{ textDecoration: 'none' }}>
          <div style={{ background: 'rgba(0,210,255,0.04)', border: '1px solid rgba(0,210,255,0.15)', borderRadius: '12px', padding: '1.5rem' }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00d2ff', margin: '0 0 8px' }}>Find Talent →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Search discoverable job seeker profiles by skill</p>
          </div>
        </Link>
        <Link to="/directory" style={{ textDecoration: 'none' }}>
          <div style={{ background: 'rgba(0,255,204,0.03)', border: '1px solid rgba(0,255,204,0.12)', borderRadius: '12px', padding: '1.5rem' }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00ffcc', margin: '0 0 8px' }}>Browse Directory →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>View all talent and training programs</p>
          </div>
        </Link>
      </div>
    </div>
  )
}
