package Sistema.Reservas.pagos.controller;

import Sistema.Reservas.pagos.dto.PagoDTO;
import Sistema.Reservas.pagos.model.Pago;
import Sistema.Reservas.pagos.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagoController.class)
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pago mockPago;
    private PagoDTO mockPagoDTO;

    @BeforeEach
    void setUp() {
        // Configuración de los datos de prueba
        mockPago = new Pago();
        mockPago.setId(1L);
        mockPago.setPedidoId(100L);
        mockPago.setMonto(new BigDecimal("1500.50"));
        mockPago.setMetodoPago("Tarjeta");
        mockPago.setFechaPago(LocalDateTime.now());

        mockPagoDTO = new PagoDTO();
        mockPagoDTO.setPedidoId(100L);
        mockPagoDTO.setMonto(new BigDecimal("1500.50"));
        mockPagoDTO.setMetodoPago("Tarjeta");
    }

    @Test
    void crearPago_DeberiaDevolverPagoCreado() throws Exception {
        // Simula que el servicio procesa y retorna el objeto correctamente
        Mockito.when(pagoService.procesarPago(any(Pago.class))).thenReturn(mockPago);

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPagoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.pedidoId").value(100L))
                .andExpect(jsonPath("$.monto").value(1500.50))
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta"));
    }

    @Test
    void obtenerTodos_DeberiaDevolverListaDePagos() throws Exception {
        // Simula el retorno de una lista con nuestro pago mockeado
        Mockito.when(pagoService.obtenerTodos()).thenReturn(Arrays.asList(mockPago));

        mockMvc.perform(get("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].monto").value(1500.50));
    }

    @Test
    void obtenerPorId_DeberiaDevolverPagoExistente() throws Exception {
        Mockito.when(pagoService.obtenerPorId(1L)).thenReturn(mockPago);

        mockMvc.perform(get("/api/pagos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.pedidoId").value(100L));
    }

    @Test
    void crearPago_DeberiaDevolverBadRequest_CuandoDTOInvalido() throws Exception {
        // Crea un DTO inválido (monto nulo y método vacío) para probar las validaciones
        PagoDTO pagoInvalido = new PagoDTO();
        pagoInvalido.setPedidoId(null);

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pagoInvalido)))
                .andExpect(status().isBadRequest()); // Espera un 400 Bad Request gatillado por @Valid
    }
}