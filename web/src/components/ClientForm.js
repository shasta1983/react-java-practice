import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom'; // Importa useNavigate en lugar de useHistory
import { Form, Button, Container } from 'react-bootstrap';

const ClientForm = () => {
  const [client, setClient] = useState({ name: '', email: '' });
  const [isNew, setIsNew] = useState(true);
  const navigate = useNavigate(); // Usa useNavigate en lugar de history
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      axios.get(`/clients/${id}`)
        .then(response => {
          setClient(response.data);
          setIsNew(false);
        })
        .catch(error => console.error(error));
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setClient(prevClient => ({ ...prevClient, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (isNew) {
      axios.post('/clients', client)
        .then(() => navigate('/')) // Usa navigate en lugar de history.push
        .catch(error => console.error(error));
    } else {
      axios.put(`/clients/${id}`, client)
        .then(() => navigate('/')) // Usa navigate en lugar de history.push
        .catch(error => console.error(error));
    }
  };

  return (
    <Container>
      <h1 className="mb-4">{isNew ? 'Create Client' : 'Edit Client'}</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="formClientName">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="text"
            name="name"
            value={client.name}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group controlId="formClientEmail" className="mt-3">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            name="email"
            value={client.email}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Button variant="primary" type="submit" className="mt-3">
          {isNew ? 'Create' : 'Update'}
        </Button>
      </Form>
    </Container>
  );
};

export default ClientForm;