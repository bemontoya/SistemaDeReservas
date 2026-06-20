package Sistema.Reservas.reportes.controller;

import Sistema.Reservas.reportes.dto.ReportesDTO;
import Sistema.Reservas.reportes.model.Reportes;
import Sistema.Reservas.reportes.Service.ReportesService;
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

@WebMvcTest(ReportesController.class)
public class ReportesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportesService reportesService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reportes mockReporte;
    private ReportesDTO mockReportesDTO;

    @BeforeEach
    void setUp() {
        mockReporte = new Reportes();
        mockReporte.setId(1L);
        mockReporte.setNombre("Reporte Mensual de Ventas");
        mockReporte.setTipo("VENTAS");
        mockReporte.setContenido("{\"totalVentas\": 5000.25, \"pedidosProcesados\": 120}");
        mockReporte.setGeneradoPor("admin_user");
        mockReporte.setFechaGeneracion(LocalDateTime.now());

        mockReportesDTO = new ReportesDTO();
        mockReportesDTO.setNombre("Reporte Mensual de Ventas");
        mockReportesDTO.setTipo("VENTAS");
        mockReportesDTO.setContenido("{\"totalVentas\": 5000.25, \"pedidosProcesados\": 120}");
        mockReportesDTO.setGeneradoPor("admin_user");
    }

    @Test
    void registrarReporte_DeberiaDevolverReporteCreado() throws Exception {
        Mockito.when(reportesService.guardarReporte(any(Reportes.class))).thenReturn(mockReporte);

        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReportesDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Reporte Mensual de Ventas"))
                .andExpect(jsonPath("$.tipo").value("VENTAS"))
                .andExpect(jsonPath("$.contenido").value("{\"totalVentas\": 5000.25, \"pedidosProcesados\": 120}"))
                .andExpect(jsonPath("$.generadoPor").value("admin_user"));
    }

    @Test
    void listarHistorial_DeberiaDevolverListaDeReportes() throws Exception {
        Mockito.when(reportesService.obtenerTodos()).thenReturn(Arrays.asList(mockReporte));

        mockMvc.perform(get("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Reporte Mensual de Ventas"));
    }

    @Test
    void obtenerPorId_DeberiaDevolverReporteExistente() throws Exception {
        Mockito.when(reportesService.obtenerPorId(1L)).thenReturn(mockReporte);

        mockMvc.perform(get("/api/reportes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Reporte Mensual de Ventas"));
    }

    @Test
    void actualizarReporte_DeberiaDevolverReporteModificado() throws Exception {
        Mockito.when(reportesService.actualizarReporte(eq(1L), any(Reportes.class))).thenReturn(mockReporte);

        mockMvc.perform(put("/api/reportes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockReportesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Reporte Mensual de Ventas"));
    }

    @Test
    void eliminarReporte_DeberiaDevolverNoContent() throws Exception {
        Mockito.doNothing().when(reportesService).eliminarPorId(1L);

        mockMvc.perform(delete("/api/reportes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void registrarReporte_DeberiaDevolverBadRequest_CuandoDTOInvalido() throws Exception {
        ReportesDTO dtoInvalido = new ReportesDTO();
        dtoInvalido.setNombre("");
        dtoInvalido.setTipo("");

        mockMvc.perform(post("/api/reportes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerPorId_DeberiaDevolverNotFound_CuandoNoExiste() throws Exception {
        Mockito.when(reportesService.obtenerPorId(99L)).thenThrow(new RuntimeException("Reporte no encontrado"));

        mockMvc.perform(get("/api/reportes/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}