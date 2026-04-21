import { useAuth } from '../context/AuthContext'
import { Link } from 'react-router-dom'
import { useEffect, useState } from 'react'

function useCountUp(target, duration = 1000) {
  const [count, setCount] = useState(0)
  useEffect(() => {
    let start = 0
    const step = target / (duration / 16)
    const timer = setInterval(() => {
      start += step
      if (start >= target) {
        setCount(target)
        clearInterval(timer)
      } else {
        setCount(Math.floor(start))
      }
    }, 16)
    return () => clearInterval(timer)
  }, [target, duration])
  return count
}

const STATS = [
  { label: 'Active Searches', target: 3, color: '#00d2ff' },
  { label: 'AI Recommendations', target: 7, color: '#c472f0' },
  { label: 'Skills Matched', target: 12, color: '#00ffcc' },
]

function StatCard({ label, target, color }) {
  const count = useCountUp(target)
  return (
    <div style={{
      background: 'rgba(0,210,255,0.03)',
      border: '1px solid rgba(0,210,255,0.1)',
      borderRadius: '12px',
      padding: '1.25rem',
    }}>
      <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '11px', color: 'rgba(255,255,255,0.4)', letterSpacing: '0.1em', textTransform: 'uppercase', margin: '0 0 8px' }}>{label}</p>
      <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '32px', fontWeight: 800, color, margin: 0 }}>{count}</p>
    </div>
  )
}

export default function JobSeekerDashboard() {
  const { user } = useAuth()

  return (
    <div style={{ padding: '2rem', maxWidth: '900px', margin: '0 auto' }}>
      <div style={{ marginBottom: '2rem' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '28px', fontWeight: 800, color: '#fff', margin: '0 0 6px' }}>
          Welcome back, <span style={{ color: '#00d2ff' }}>{user.name}.</span>
        </h1>
        <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '14px', color: 'rgba(255,255,255,0.4)', margin: 0, letterSpacing: '0.06em' }}>
          YOUR WORKFORCE INTELLIGENCE DASHBOARD
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '1rem', marginBottom: '2rem' }}>
        {STATS.map(stat => (
          <StatCard key={stat.label} {...stat} />
        ))}
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
        <Link to="/search" style={{ textDecoration: 'none' }}>
          <div style={{
            background: 'rgba(0,210,255,0.04)',
            border: '1px solid rgba(0,210,255,0.15)',
            borderRadius: '12px',
            padding: '1.5rem',
            cursor: 'pointer',
            transition: 'border-color 0.2s',
          }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00d2ff', margin: '0 0 8px' }}>Search Roles →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Find roles and get AI-powered gap analysis</p>
          </div>
        </Link>
        <Link to="/profile" style={{ textDecoration: 'none' }}>
          <div style={{
            background: 'rgba(196,114,240,0.04)',
            border: '1px solid rgba(196,114,240,0.15)',
            borderRadius: '12px',
            padding: '1.5rem',
            cursor: 'pointer',
          }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#c472f0', margin: '0 0 8px' }}>Update Profile →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Keep your skills current to get better matches</p>
          </div>
        </Link>
        <Link to="/history" style={{ textDecoration: 'none' }}>
          <div style={{
            background: 'rgba(0,255,204,0.03)',
            border: '1px solid rgba(0,255,204,0.12)',
            borderRadius: '12px',
            padding: '1.5rem',
            cursor: 'pointer',
          }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00ffcc', margin: '0 0 8px' }}>View History →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Review your past AI recommendations</p>
          </div>
        </Link>
        <Link to="/directory" style={{ textDecoration: 'none' }}>
          <div style={{
            background: 'rgba(0,210,255,0.03)',
            border: '1px solid rgba(0,210,255,0.1)',
            borderRadius: '12px',
            padding: '1.5rem',
            cursor: 'pointer',
          }}>
            <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '16px', fontWeight: 700, color: '#00d2ff', margin: '0 0 8px' }}>Browse Directory →</p>
            <p style={{ fontFamily: 'Space Grotesk, sans-serif', fontSize: '13px', color: 'rgba(255,255,255,0.4)', margin: 0 }}>Explore training programs and employers</p>
          </div>
        </Link>
      </div>
    </div>
  )
}