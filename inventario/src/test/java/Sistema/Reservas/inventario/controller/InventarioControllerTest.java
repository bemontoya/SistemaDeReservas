package Sistema.Reservas.inventario.controller;

import Sistema.Reservas.inventario.dto.InventarioDTO;
import Sistema.Reservas.inventario.model.Inventario;
import Sistema.Reservas.inventario.service.InventarioService;
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

public class InventarioControllerTest {

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private InventarioController inventarioController;

    private Inventario insumoMock;
    private InventarioDTO dtoMock;

    @BeforeEach
    public void setUp() {
        // Inicializa los objetos @Mock de Mockito
        MockitoAnnotations.openMocks(this);

        // Insumo base para las simulaciones (Ej: Carne de Vacuno con buen stock)
        insumoMock = new Inventario();
        insumoMock.setId(1L);
        insumoMock.setNombreIngrediente("Carne Vacuno");
        insumoMock.setCantidadActual(50);
        insumoMock.setStockMinimo(10);
        insumoMock.setUnidadMedida("Kg");

        // DTO base que simula la entrada de datos desde el cliente (Swagger)
        dtoMock = new InventarioDTO();
        dtoMock.setNombreIngrediente("Carne Vacuno");
        dtoMock.setCantidadActual(50);
        dtoMock.setStockMinimo(10);
        dtoMock.setUnidadMedida("Kg");
    }

    @Test
    public void testListarTodos() {
        List<Inventario> listaFalsa = new ArrayList<>();
        listaFalsa.add(insumoMock);

        when(inventarioService.obtenerTodo()).thenReturn(listaFalsa);

        List<Inventario> resultado = inventarioController.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(inventarioService, times(1)).obtenerTodo();
    }

    @Test
    public void testObtenerPorIdExitoso() {
        when(inventarioService.obtenerPorId(1L)).thenReturn(insumoMock);

        ResponseEntity<Inventario> respuesta = inventarioController.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(1L, respuesta.getBody().getId());
    }

    @Test
    public void testObtenerPorIdNoEncontrado() {
        when(inventarioService.obtenerPorId(99L))
                .thenThrow(new RuntimeException("Insumo no encontrado con el ID: 99"));

        ResponseEntity<Inventario> respuesta = inventarioController.obtenerPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    public void testListarCriticos() {
        List<Inventario> listaCriticaFalsa = new ArrayList<>();

        // Creamos un insumo que deliberadamente está en estado crítico
        Inventario insumoCritico = new Inventario();
        insumoCritico.setId(2L);
        insumoCritico.setNombreIngrediente("Arroz");
        insumoCritico.setCantidadActual(5);  // Igual al stock mínimo, gatilla alerta
        insumoCritico.setStockMinimo(5);
        insumoCritico.setUnidadMedida("Kg");
        listaCriticaFalsa.add(insumoCritico);

        when(inventarioService.obtenerCriticos()).thenReturn(listaCriticaFalsa);

        List<Inventario> resultado = inventarioController.listarCriticos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getCantidadActual() <= resultado.get(0).getStockMinimo());
        verify(inventarioService, times(1)).obtenerCriticos();
    }

    @Test
    public void testCrearInsumo() {
        when(inventarioService.crearInsumo(any(Inventario.class))).thenReturn(insumoMock);

        ResponseEntity<Inventario> respuesta = inventarioController.crear(dtoMock);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Carne Vacuno", respuesta.getBody().getNombreIngrediente());
    }

    @Test
    public void testActualizarInsumoExitoso() {
        when(inventarioService.obtenerPorId(1L)).thenReturn(insumoMock);
        when(inventarioService.actualizarInsumo(any(Inventario.class))).thenReturn(insumoMock);

        ResponseEntity<Inventario> respuesta = inventarioController.actualizar(1L, dtoMock);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    public void testDescontarStockExitoso() {
        // Clonamos y alteramos el mock para simular que restamos 10 Kg exitosamente
        Inventario insumoModificado = new Inventario();
        insumoModificado.setId(1L);
        insumoModificado.setNombreIngrediente("Carne Vacuno");
        insumoModificado.setCantidadActual(40); // 50 - 10
        insumoModificado.setStockMinimo(10);

        when(inventarioService.descontarStock(1L, 10)).thenReturn(insumoModificado);

        ResponseEntity<?> respuesta = inventarioController.descontar(1L, 10);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        verify(inventarioService, times(1)).descontarStock(1L, 10);
    }

    @Test
    public void testDescontarStockInsuficiente() {
        // Simulamos que el service lanza la excepción de negocio por falta de insumos
        when(inventarioService.descontarStock(1L, 100))
                .thenThrow(new RuntimeException("Stock insuficiente para el insumo: Carne Vacuno"));

        ResponseEntity<?> respuesta = inventarioController.descontar(1L, 100);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    public void testEliminarInsumoExitoso() {
        when(inventarioService.obtenerPorId(1L)).thenReturn(insumoMock);
        doNothing().when(inventarioService).eliminarPorId(1L);

        ResponseEntity<Void> respuesta = inventarioController.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(inventarioService, times(1)).eliminarPorId(1L);
    }
}