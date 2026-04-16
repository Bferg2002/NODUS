import { Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import ProtectedRoute from './router/ProtectedRoute'

const Placeholder = ({ name }) => (
  <div style={{ color: 'white', padding: '2rem' }}>{name} page</div>
)

function App() {
  return (
    <div>
      <Navbar />
      <Routes>
        <Route path="/" element={<Placeholder name="Landing" />} />
        <Route path="/login" element={<Placeholder name="Login" />} />
        <Route path="/register" element={<Placeholder name="Register" />} />
        
        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<Placeholder name="Dashboard" />} />
          <Route path="/search" element={<Placeholder name="Search" />} />
          <Route path="/profile" element={<Placeholder name="Profile" />} />
          <Route path="/directory" element={<Placeholder name="Directory" />} />
          <Route path="/history" element={<Placeholder name="History" />} />
        </Route>
      </Routes>
    </div>
  )
}

export default App