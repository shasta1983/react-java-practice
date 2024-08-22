import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useParams } from 'react-router-dom';
import { Container, Button } from 'react-bootstrap'; // Keep this if you use Button

const ClientDetail = () => {
  const [client, setClient] = useState(null);
  const { id } = useParams();

  useEffect(() => {
    axios.get(`/clients/${id}`)
      .then(response => setClient(response.data))
      .catch(error => console.error(error));
  }, [id]);

  if (!client) return <div>Loading...</div>;

  return (
    <Container>
      <h1 className="mb-4">Client Details</h1>
      <div className="mb-3">
        <strong>Name:</strong> {client.name}
      </div>
      <div className="mb-3">
        <strong>Email:</strong> {client.email}
      </div>
      <Button variant="warning" as={Link} to={`/edit/${id}`} className="mr-2">Edit</Button>
      <Button variant="secondary" as={Link} to="/">Back to List</Button>
    </Container>
  );
};

export default ClientDetail;
