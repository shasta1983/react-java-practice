import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ClientList from './components/ClientList';
import ClientForm from './components/ClientForm';
import ClientDetail from './components/ClientDetail';
import NavBar from './components/Navbar';

function App() {
  return (
    <Router>
      <div>
        <NavBar />
        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<ClientList />} />
            <Route path="/clients/:id" element={<ClientDetail />} />
            <Route path="/edit/:id" element={<ClientForm />} />
            <Route path="/create" element={<ClientForm />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;