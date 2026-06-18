package Sistema.Reservas.mesa.controller;

import Sistema.Reservas.mesa.dto.MesaDTO;
import Sistema.Reservas.mesa.model.Mesa;
import Sistema.Reservas.mesa.service.MesaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MesaController.class)
public class MesaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MesaService mesaService;

    @Autowired
    private ObjectMapper objectMapper;

    private MesaDTO mesaDTO;
    private Mesa mesaGuardada;

    @BeforeEach
    void setUp() {
        // Preparar los datos de entrada simulan el JSON enviado desde Swagger
        mesaDTO = new MesaDTO();
        mesaDTO.setNumeroMesa(5);
        mesaDTO.setCapacity(4); // Se usa 'capacity' en inglés según el DTO

        // Preparar la entidad que se supone que retorna la Base de Datos simulada
        mesaGuardada = new Mesa();
        mesaGuardada.setId(1L);
        mesaGuardada.setNumeroMesa(5);
        mesaGuardada.setCapacidad(4);
        mesaGuardada.setEstado("LIBRE");
    }

    @Test
    void crearMesa_DeberiaRetornarMesaCreadaYStatus201() throws Exception {
        // Se simula el comportamiento del servicio: cuando le pidan crear una mesa, retorna la mesa armada
        Mockito.when(mesaService.crearMesa(any(Mesa.class))).thenReturn(mesaGuardada);

        // Se ejecuta la petición POST simulada hacia el endpoint
        mockMvc.perform(post("/api/mesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mesaDTO)))

                // Se espera un HTTP 201 Created y que el JSON traiga los datos correctos
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.numeroMesa").value(5))
                .andExpect(jsonPath("$.capacidad").value(4))
                .andExpect(jsonPath("$.estado").value("LIBRE"));
    }
}