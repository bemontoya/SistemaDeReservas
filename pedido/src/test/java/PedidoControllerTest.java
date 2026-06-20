package Sistema.Reservas.pedido.controller;

import Sistema.Reservas.pedido.dto.PedidoDTO;
import Sistema.Reservas.pedido.model.Pedido;
import Sistema.Reservas.pedido.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido mockPedido;
    private PedidoDTO mockPedidoDTO;

    @BeforeEach
    void setUp() {
        // Configuración de los datos de prueba simulados
        mockPedido = new Pedido();
        mockPedido.setId(1L);
        mockPedido.setClienteId(10L);
        mockPedido.setReservaId(5L);
        mockPedido.setDetalle("1x Pizza Familiar, 2x Bebidas Cola");
        mockPedido.setTotal(0.0);
        mockPedido.setEstado("PENDIENTE");
        mockPedido.setFechaPedido(LocalDateTime.now());

        mockPedidoDTO = new PedidoDTO();
        mockPedidoDTO.setClienteId(10L);
        mockPedidoDTO.setReservaId(5L);
        mockPedidoDTO.setDetalle("1x Pizza Familiar, 2x Bebidas Cola");
    }

    @Test
    void crearPedido_DeberiaDevolverPedidoCreado() throws Exception {
        Mockito.when(pedidoService.crearPedido(any(Pedido.class))).thenReturn(mockPedido);

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPedidoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clienteId").value(10L))
                .andExpect(jsonPath("$.reservaId").value(5L))
                .andExpect(jsonPath("$.detalle").value("1x Pizza Familiar, 2x Bebidas Cola"))
                .andExpect(jsonPath("$.total").value(0.0))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void listarTodos_DeberiaDevolverListaDePedidos() throws Exception {
        Mockito.when(pedidoService.obtenerTodos()).thenReturn(Arrays.asList(mockPedido));

        mockMvc.perform(get("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].detalle").value("1x Pizza Familiar, 2x Bebidas Cola"));
    }

    @Test
    void obtenerPorId_DeberiaDevolverPedidoExistente() throws Exception {
        Mockito.when(pedidoService.obtenerPorId(1L)).thenReturn(mockPedido);

        mockMvc.perform(get("/api/pedidos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clienteId").value(10L));
    }

    @Test
    void actualizarPedido_DeberiaDevolverPedidoModificado() throws Exception {
        Mockito.when(pedidoService.actualizarPedido(eq(1L), any(Pedido.class))).thenReturn(mockPedido);

        mockMvc.perform(put("/api/pedidos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPedidoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.detalle").value("1x Pizza Familiar, 2x Bebidas Cola"));
    }

    @Test
    void eliminarPedido_DeberiaDevolverNoContent() throws Exception {
        Mockito.doNothing().when(pedidoService).eliminarPorId(1L);

        mockMvc.perform(delete("/api/pedidos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Espera un 204 No Content
    }

    @Test
    void crearPedido_DeberiaDevolverBadRequest_CuandoDTOInvalido() throws Exception {
        // Crea un DTO inválido (con campos nulos y vacíos obligatorios)
        PedidoDTO pedidoInvalido = new PedidoDTO();
        pedidoInvalido.setClienteId(null);
        pedidoInvalido.setReservaId(null);
        pedidoInvalido.setDetalle(""); // Gatilla el @NotBlank

        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerPorId_DeberiaDevolverNotFound_CuandoNoExiste() throws Exception {
        // Simula que el servicio lanza una excepción cuando el pedido no existe
        Mockito.when(pedidoService.obtenerPorId(99L)).thenThrow(new RuntimeException("Pedido no encontrado"));

        mockMvc.perform(get("/api/pedidos/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Valida que tu ExceptionHandler responda con un 404
    }
}