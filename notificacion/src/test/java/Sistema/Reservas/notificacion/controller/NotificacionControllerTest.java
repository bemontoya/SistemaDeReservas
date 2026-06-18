package Sistema.Reservas.notificacion.controller;

import Sistema.Reservas.notificacion.dto.NotificacionDTO;
import Sistema.Reservas.notificacion.model.Notificacion;
import Sistema.Reservas.notificacion.service.NotificacionService;
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

public class NotificacionControllerTest {

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private NotificacionController notificacionController;

    private Notificacion notificacionMock;
    private NotificacionDTO dtoMock;

    @BeforeEach
    public void setUp() {
        // Inicializa los objetos @Mock de Mockito
        MockitoAnnotations.openMocks(this);

        // Notificación base para las simulaciones
        notificacionMock = new Notificacion();
        notificacionMock.setId(1L);
        notificacionMock.setDestinatario("cliente@correo.com");
        notificacionMock.setMensaje("Tu mesa #5 ha sido reservada con éxito.");
        notificacionMock.setTipo("EMAIL");

        // DTO base que simula la entrada desde el cliente (Swagger)
        dtoMock = new NotificacionDTO();
        dtoMock.setDestinatario("cliente@correo.com");
        dtoMock.setMensaje("Tu mesa #5 ha sido reservada con éxito.");
        dtoMock.setTipo("EMAIL");
    }

    @Test
    public void testListarTodas() {
        List<Notificacion> listaFalsa = new ArrayList<>();
        listaFalsa.add(notificacionMock);

        when(notificacionService.obtenerTodas()).thenReturn(listaFalsa);

        // Cambiado a List<Notificacion> para que calce con tu controlador
        List<Notificacion> resultado = notificacionController.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(notificacionService, times(1)).obtenerTodas();
    }

    @Test
    public void testListarPorDestinatario() {
        List<Notificacion> listaFiltradaFalsa = new ArrayList<>();
        listaFiltradaFalsa.add(notificacionMock);

        when(notificacionService.obtenerPorDestinatario("cliente@correo.com")).thenReturn(listaFiltradaFalsa);

        // Cambiado a List<Notificacion> para que calce con tu controlador
        List<Notificacion> resultado = notificacionController.listarPorDestinatario("cliente@correo.com");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("cliente@correo.com", resultado.get(0).getDestinatario());
        verify(notificacionService, times(1)).obtenerPorDestinatario("cliente@correo.com");
    }

    @Test
    public void testEnviarNotificacion() {
        when(notificacionService.enviarNotificacion(any(Notificacion.class))).thenReturn(notificacionMock);

        // Pasamos el dtoMock y cambiamos la validación a HttpStatus.CREATED (201)
        ResponseEntity<Notificacion> respuesta = notificacionController.crear(dtoMock);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Tu mesa #5 ha sido reservada con éxito.", respuesta.getBody().getMensaje()); // Corregido getMensaje()
        verify(notificacionService, times(1)).enviarNotificacion(any(Notificacion.class));
    }
}