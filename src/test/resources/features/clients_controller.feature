Feature: Clients API

  Scenario: Obtener todos los clientes
    Given El repositorio contiene clientes
    When Se llama a la API de obtener todos los clientes
    Then Se retorna la lista de clientes

  Scenario: Obtener un cliente por ID
    Given El repositorio contiene un cliente con ID 1
    When Se llama a la API de obtener el cliente con ID 1
    Then Se retorna el cliente con ID 1

  Scenario: Crear un cliente
    Given Un cliente válido
    When Se llama a la API de crear cliente
    Then El cliente es creado correctamente

  Scenario: Actualizar un cliente
    Given Un cliente con ID 1
    When Se llama a la API de actualizar el cliente con ID 1
    Then El cliente es actualizado correctamente

  Scenario: Eliminar un cliente
    Given Un cliente con ID 1
    When Se llama a la API de eliminar el cliente con ID 1
    Then El cliente es eliminado correctamente
