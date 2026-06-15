package Sistema.Reservas.empleado.controller;

import Sistema.Reservas.empleado.dto.EmpleadoDTO;
import Sistema.Reservas.empleado.model.Empleado;
import Sistema.Reservas.empleado.service.EmpleadoService;
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

public class EmpleadoControllerTest {

    // Simulador falso de la capa de servicio de empleados
    @Mock
    private EmpleadoService empleadoService;

    // Se inyecta el simulador dentro del controlador real de empleados
    @InjectMocks
    private EmpleadoController empleadoController;

    private Empleado empleadoMock;
    private EmpleadoDTO dtoMock;

    // Configuración previa a cada test
    @BeforeEach
    public void setUp() {
        // Inicializa los objetos @Mock de Mockito
        MockitoAnnotations.openMocks(this);

        // Empleado falso que simula venir de la Base de Datos
        empleadoMock = new Empleado();
        empleadoMock.setId(1L);
        empleadoMock.setNombre("Carlos");
        empleadoMock.setApellido("Munoz");
        empleadoMock.setCargo("Garzon");
        empleadoMock.setEmail("carlos.munoz@mail.cl");

        // DTO falso que simula venir desde la petición HTTP (Swagger/Postman)
        dtoMock = new EmpleadoDTO();
        dtoMock.setNombre("Carlos");
        dtoMock.setApellido("Munoz");
        dtoMock.setCargo("Garzon");
        dtoMock.setEmail("carlos.munoz@mail.cl");
    }

    @Test
    public void testListarTodos() {
        List<Empleado> listaFalsa = new ArrayList<>();
        listaFalsa.add(empleadoMock);

        // Cuando llamen a obtenerTodos, entrega la listaFalsa
        when(empleadoService.obtenerTodos()).thenReturn(listaFalsa);

        List<Empleado> resultado = empleadoController.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(empleadoService, times(1)).obtenerTodos();
    }

    @Test
    public void testObtenerPorIdExitoso() {
        when(empleadoService.obtenerPorId(1L)).thenReturn(empleadoMock);

        ResponseEntity<Empleado> respuesta = empleadoController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        when(empleadoService.obtenerPorId(99L)).thenThrow(new RuntimeException("Empleado no encontrado con el ID: 99"));

        ResponseEntity<Empleado> respuesta = empleadoController.obtenerPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    public void testCrearEmpleado() {
        when(empleadoService.crearEmpleado(any(Empleado.class))).thenReturn(empleadoMock);

        ResponseEntity<Empleado> respuesta = empleadoController.crear(dtoMock);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Carlos", respuesta.getBody().getNombre());
    }

    @Test
    public void testActualizarEmpleadoExitoso() {
        when(empleadoService.obtenerPorId(1L)).thenReturn(empleadoMock);
        when(empleadoService.actualizarEmpleado(any(Empleado.class))).thenReturn(empleadoMock);

        ResponseEntity<Empleado> respuesta = empleadoController.actualizar(1L, dtoMock);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    public void testEliminarEmpleadoExitoso() {
        when(empleadoService.obtenerPorId(1L)).thenReturn(empleadoMock);
        doNothing().when(empleadoService).eliminarPorId(1L);

        ResponseEntity<Void> respuesta = empleadoController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(empleadoService, times(1)).eliminarPorId(1L);
    }
}