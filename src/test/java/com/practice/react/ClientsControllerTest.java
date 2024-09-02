package com.practice.react;

import com.practice.react.model.Client;
import com.practice.react.repositorory.ClientRepository;
import com.practice.react.rest.ClientsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

public class ClientsControllerTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientsController clientsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClients() {
        // Crear una lista de clientes
        List<Client> clients = Arrays.asList(
                new Client(1L, "John Doe", "john@example.com"),
                new Client(2L, "Jane Doe", "jane@example.com")
        );
        // Configurar el mock para devolver la lista
        when(clientRepository.findAll()).thenReturn(clients);

        // Ejecutar la prueba
        List<Client> result = clientsController.getClients();

        // Verificar resultados
        assertEquals(2, result.size());
    }

    @Test
    void testGetClient() {
        // Crear un cliente
        Client client = new Client(1L, "John Doe", "john@example.com");
        // Configurar el mock para devolver el cliente
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Ejecutar la prueba
        Client result = clientsController.getClient(1L);

        // Verificar resultados
        assertNotNull(result);
    }

    @Test
    void testCreateClient() throws URISyntaxException {
        // Crear un cliente sin ID
        Client client = new Client(null, "John Doe", "john@example.com");
        // Crear un cliente con ID para simular la respuesta
        Client savedClient = new Client(1L, "John Doe", "john@example.com");

        // Configurar el mock para devolver el cliente guardado
        when(clientRepository.save(client)).thenReturn(savedClient);

        // Ejecutar la prueba
        ResponseEntity<Client> response = clientsController.createClient(client);

        // Verificar resultados
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(savedClient, response.getBody());
    }

    @Test
    void testUpdateClient() {
        // Crear un cliente existente y uno actualizado
        Client existingClient = new Client(1L, "John Doe", "john@example.com");
        Client updatedClient = new Client(1L, "Jane Doe", "jane@example.com");

        // Configurar los mocks
        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(updatedClient)).thenReturn(updatedClient);

        // Ejecutar la prueba
        ResponseEntity<Client> response = clientsController.updateClient(1L, updatedClient);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    void testDeleteClient() {
        // Configurar el mock para el método deleteById
        Mockito.doNothing().when(clientRepository).deleteById(1L);

        // Ejecutar la prueba
        ResponseEntity<Void> response = clientsController.deleteClient(1L);

        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        verify(clientRepository).deleteById(1L);
    }
}


