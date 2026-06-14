package Sistema.Reservas.cliente.controller;

import Sistema.Reservas.cliente.dto.ClienteDTO;
import Sistema.Reservas.cliente.model.Cliente;
import Sistema.Reservas.cliente.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteControllerTest {

    //Simulador falso de la capa de servicio
    @Mock
    private ClienteService clienteService;

    // Se inyecta el simulador dentro del controlador real
    @InjectMocks
    private ClienteController clienteController;

    private Cliente clienteMock;
    private ClienteDTO dtoMock;

    // Configuración previa a cada test
    @BeforeEach
    public void setUp() {
        // Inicializa los objetos @Mock de Mockito
        MockitoAnnotations.openMocks(this);

        // Cliente falso que simula venir de la Base de Datos
        clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNombre("Pedro");
        clienteMock.setApellido("Aguirre");
        clienteMock.setEmail("pedro@mail.cl");
        clienteMock.setTelefono("+56987654321");

        // DTO falso que simula venir de Postman
        dtoMock = new ClienteDTO();
        dtoMock.setNombre("Pedro");
        dtoMock.setApellido("Aguirre");
        dtoMock.setEmail("pedro@mail.cl");
        dtoMock.setTelefono("+56987654321");
    }


    @Test
    public void testListarTodos() {
        List<Cliente> listaFalsa = new ArrayList<>();
        listaFalsa.add(clienteMock);

        // Cuando llamen a obtenerTodos, entrega la listaFalsa
        when(clienteService.obtenerTodos()).thenReturn(listaFalsa);

        List<Cliente> resultado = clienteController.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteService, times(1)).obtenerTodos();
    }


    @Test
    public void testObtenerPorIdExitoso() {
        when(clienteService.obtenerPorId(1L)).thenReturn(clienteMock);

        ResponseEntity<Cliente> respuesta = clienteController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        when(clienteService.obtenerPorId(99L)).thenThrow(new RuntimeException("Cliente no encontrado"));

        ResponseEntity<Cliente> respuesta = clienteController.obtenerPorId(99L);


        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    public void testCrearCliente() {

        when(clienteService.crearCliente(any(Cliente.class))).thenReturn(clienteMock);

        ResponseEntity<Cliente> respuesta = clienteController.crear(dtoMock);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }
}