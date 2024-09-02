package com.practice.react;

import com.practice.react.model.Client;
import com.practice.react.repositorory.ClientRepository;
import com.practice.react.rest.ClientsController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ClientsController.class)
public class StepDefinitions {

    @Autowired
    private ClientsController clientsController;

    @MockBean
    private ClientRepository clientRepository;

    private ResponseEntity<?> response;

    @Given("El repositorio contiene clientes")
    public void elRepositorioContieneClientes() {
        List<Client> clients = Arrays.asList(
                new Client(1L, "John Doe", "john@example.com"),
                new Client(2L, "Jane Doe", "jane@example.com")
        );
        given(clientRepository.findAll()).willReturn(clients);
    }

    @When("Se llama a la API de obtener todos los clientes")
    public void seLlamaALaApiDeObtenerTodosLosClientes() {
        response = ResponseEntity.ok(clientsController.getClients());
    }

    @Then("Se retorna la lista de clientes")
    public void seRetornaLaListaDeClientes() {
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isInstanceOf(List.class);
        assertThat(((List<?>) response.getBody()).size()).isEqualTo(2);
    }

    @Given("El repositorio contiene un cliente con ID {int}")
    public void elRepositorioContieneUnClienteConID(int id) {
        Client client = new Client((long) id, "John Doe", "john@example.com");
        given(clientRepository.findById((long) id)).willReturn(Optional.of(client));
    }

    @When("Se llama a la API de obtener el cliente con ID {int}")
    public void seLlamaALaApiDeObtenerElClienteConID(int id) {
        response = ResponseEntity.ok(clientsController.getClient((long) id));
    }

    @Then("Se retorna el cliente con ID {int}")
    public void seRetornaElClienteConID(int id) {
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Client client = (Client) response.getBody();
        assertThat(client.getId()).isEqualTo((long) id);
    }

    @Given("Un cliente válido")
    public void unClienteValido() {
        Client client = new Client(1L, "John Doe", "john@example.com");
        given(clientRepository.save(client)).willReturn(new Client(3L, "John Doe", "john@example.com"));
    }

    @When("Se llama a la API de crear cliente")
    public void seLlamaALaApiDeCrearCliente() throws URISyntaxException {
        Client client = new Client(1L, "John Doe", "john@example.com");
        response = clientsController.createClient(client);
    }

    @Then("El cliente es creado correctamente")
    public void elClienteEsCreadoCorrectamente() throws URISyntaxException {
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getHeaders().getLocation()).isEqualTo(new URI("/clients/3"));
    }

    @Given("Un cliente con ID {int}")
    public void unClienteConID(int id) {
        Client client = new Client((long) id, "John Doe", "john@example.com");
        given(clientRepository.findById((long) id)).willReturn(Optional.of(client));
    }

    @When("Se llama a la API de actualizar el cliente con ID {int}")
    public void seLlamaALaApiDeActualizarElClienteConID(int id) {
        Client client = new Client((long) id, "Jane Doe", "jane@example.com");
        response = clientsController.updateClient((long) id, client);
    }

    @Then("El cliente es actualizado correctamente")
    public void elClienteEsActualizadoCorrectamente() {
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Client client = (Client) response.getBody();
        assertThat(client.getName()).isEqualTo("Jane Doe");
    }

    @Given("Un cliente con ID {int}")
    public void unClienteConIDParaEliminar(int id) {
        Client client = new Client((long) id, "John Doe", "john@example.com");
        given(clientRepository.findById((long) id)).willReturn(Optional.of(client));
    }

    @When("Se llama a la API de eliminar el cliente con ID {int}")
    public void seLlamaALaApiDeEliminarElClienteConID(int id) {
        response = clientsController.deleteClient((long) id);
    }

    @Then("El cliente es eliminado correctamente")
    public void elClienteEsEliminadoCorrectamente() {
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
