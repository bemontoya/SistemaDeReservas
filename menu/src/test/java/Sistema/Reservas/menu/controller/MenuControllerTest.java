package Sistema.Reservas.menu.controller;

import Sistema.Reservas.menu.model.Menu;
import Sistema.Reservas.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    private Menu platoPrueba;

    @BeforeEach
    void setUp() {
        platoPrueba = new Menu();
        platoPrueba.setId(1L);
        platoPrueba.setNombre("Lomo Saltado");
        platoPrueba.setDescripcion("Delicioso lomo vacuno con papas fritas");
        platoPrueba.setPrecio(new BigDecimal("12500.00"));
        platoPrueba.setCategoria("Fondo");
        platoPrueba.setDisponible(true);
    }

    @Test
    void listarTodo_DeberiaRetornarListaDePlatos() throws Exception {
        // Simular que el servicio retorna la lista con el plato de prueba
        Mockito.when(menuService.obtenerTodos()).thenReturn(Arrays.asList(platoPrueba));

        // Ejecutar la petición GET a /api/menu y validar la respuesta
        mockMvc.perform(get("/api/menu")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Lomo Saltado"))
                .andExpect(jsonPath("$[0].categoria").value("Fondo"));
    }
}