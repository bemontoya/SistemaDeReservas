package Sistema.Reservas.reserva.controller;

import Sistema.Reservas.reserva.dto.ReservaDTO;
import Sistema.Reservas.reserva.model.Reserva;
import Sistema.Reservas.reserva.service.ReservaService;
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

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reserva mockReserva;
    private ReservaDTO mockReservaDTO;
    private LocalDateTime fechaFutura;

    @BeforeEach
    void setUp() {
        fechaFutura = LocalDateTime.now().plusDays(2);

        mockReserva = new Reserva();
        mockReserva.setId(1L);
        mockReserva.setClienteId(10L);
        mockReserva.setMesaId(5L);
        mockReserva.setFechaReserva(fechaFutura);
        mockReserva.setCantidadPersonas(4);
        mockReserva.setEstado("CONFIRMADA");

        mockReservaDTO = new ReservaDTO();
        mockReservaDTO.setClienteId(10L);
        mockReservaDTO.setMesaId(5L);
        mockReservaDTO.setFechaReserva(fechaFutura);
        mockReservaDTO.setCantidadPersonas(4);
    }

    @Test
    void crearReserva_DeberiaDevolverReservaCreada() throws Exception {
        Mockito.when(reservaService.crearReserva(any(Reserva.class))).thenReturn(mockReserva);

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReservaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clienteId").value(10L))
                .andExpect(jsonPath("$.mesaId").value(5L))
                .andExpect(jsonPath("$.cantidadPersonas").value(4))
                .andExpect(jsonPath("$.estado").value("CONFIRMADA"));
    }

    @Test
    void listarTodas_DeberiaDevolverListaDeReservas() throws Exception {
        Mockito.when(reservaService.obtenerTodas()).thenReturn(Arrays.asList(mockReserva));

        mockMvc.perform(get("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].cantidadPersonas").value(4));
    }

    @Test
    void obtenerPorId_DeberiaDevolverReservaExistente() throws Exception {
        Mockito.when(reservaService.obtenerPorId(1L)).thenReturn(mockReserva);

        mockMvc.perform(get("/api/reservas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.clienteId").value(10L));
    }

    @Test
    void actualizarReserva_DeberiaDevolverReservaModificada() throws Exception {
        Mockito.when(reservaService.actualizarReserva(eq(1L), any(Reserva.class))).thenReturn(mockReserva);

        mockMvc.perform(put("/api/reservas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReservaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cantidadPersonas").value(4));
    }

    @Test
    void eliminarReserva_DeberiaDevolverNoContent() throws Exception {
        Mockito.doNothing().when(reservaService).eliminarPorId(1L);

        mockMvc.perform(delete("/api/reservas/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void crearReserva_DeberiaDevolverBadRequest_CuandoDTOInvalido() throws Exception {
        ReservaDTO reservaInvalida = new ReservaDTO();
        reservaInvalida.setClienteId(null);
        reservaInvalida.setMesaId(null);
        reservaInvalida.setCantidadPersonas(0); // Gatilla el @Min(1)
        reservaInvalida.setFechaReserva(LocalDateTime.now().minusDays(1)); // Pasado: Gatilla el @Future

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservaInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerPorId_DeberiaDevolverNotFound_CuandoNoExiste() throws Exception {
        Mockito.when(reservaService.obtenerPorId(99L)).thenThrow(new RuntimeException("Reserva no encontrada"));

        mockMvc.perform(get("/api/reservas/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}