import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './ClientList.css';
import { Button, ListGroup, Container } from 'react-bootstrap';

const ClientList = () => {
  const [clients, setClients] = useState([]);

  useEffect(() => {
    axios.get('/clients')
      .then(response => setClients(response.data))
      .catch(error => console.error(error));
  }, []);

  const handleDelete = (id) => {
    axios.delete(`/clients/${id}`)
      .then(() => setClients(clients.filter(client => client.id !== id)))
      .catch(error => console.error(error));
  };

  return (
    <Container>
      <h1 className="mb-4">Clients</h1>
      <Link to="/create">
        <Button variant="primary" className="mb-3">Create New Client</Button>
      </Link>
      <ListGroup>
        {clients.map(client => (
          <ListGroup.Item key={client.id}>
            <div className="d-flex justify-content-between align-items-center">
              <div>
                {client.name} - {client.email}
              </div>
             <div>
               <Link to={`/clients/${client.id}`} className="btn btn-info btn-sm button-spacing">View</Link>
               <Link to={`/edit/${client.id}`} className="btn btn-warning btn-sm button-spacing">Edit</Link>
               <Button variant="danger" size="sm" onClick={() => handleDelete(client.id)} className="button-spacing">Delete</Button>
             </div>
            </div>
          </ListGroup.Item>
        ))}
      </ListGroup>
    </Container>
  );
};

export default ClientList;
