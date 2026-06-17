package Sistema.Reservas.menu.controller;

import Sistema.Reservas.menu.dto.MenuDTO;
import Sistema.Reservas.menu.model.Menu;
import Sistema.Reservas.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Tag(name = "Menú", description = "Controlador para la gestión completa de la carta del restaurante")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    @Operation(summary = "Obtener toda la carta", description = "Retorna la lista completa de todos los platos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de platos obtenida exitosamente")
    public List<Menu> listarTodo() {
        return menuService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un ítem por ID", description = "Obtiene los detalles de un plato específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El ítem solicitado no existe")
    })
    public ResponseEntity<Menu> obtenerPorId(@PathVariable Long id) {
        Menu plato = menuService.obtenerPorId(id);
        return ResponseEntity.ok(plato);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo plato", description = "Crea un ítem en la carta procesando las validaciones del DTO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<Menu> crear(@Valid @RequestBody MenuDTO menuDTO) {
        Menu menuEntidad = new Menu();
        menuEntidad.setNombre(menuDTO.getNombre());
        menuEntidad.setDescripcion(menuDTO.getDescripcion());
        menuEntidad.setPrecio(menuDTO.getPrecio());
        menuEntidad.setCategoria(menuDTO.getCategoria());
        menuEntidad.setDisponible(menuDTO.getDisponible());

        Menu nuevoPlato = menuService.crearPlato(menuEntidad);
        return new ResponseEntity<>(nuevoPlato, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un plato existente", description = "Modifica los datos de un plato de la carta buscando por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "El plato a actualizar no existe")
    })
    public ResponseEntity<Menu> actualizar(@PathVariable Long id, @Valid @RequestBody MenuDTO menuDTO) {
        Menu platoExistente = menuService.obtenerPorId(id);

        platoExistente.setNombre(menuDTO.getNombre());
        platoExistente.setDescripcion(menuDTO.getDescripcion());
        platoExistente.setPrecio(menuDTO.getPrecio());
        platoExistente.setCategoria(menuDTO.getCategoria());
        platoExistente.setDisponible(menuDTO.getDisponible());

        Menu platoActualizado = menuService.actualizarPlato(platoExistente);
        return ResponseEntity.ok(platoActualizado);
    }


    @GetMapping("/categoria/{categoria}")
    @Operation(
            summary = "Filtrar platos por categoría",
            description = "Retorna una lista de platos que pertenecen a una categoría específica (Ej: Entrada, Fondo, Postre, Bebestible)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "La categoría proporcionada no es válida")
    })
    public List<Menu> obtenerPorCategoria(@PathVariable String categoria) {
        return menuService.obtenerPorCategoria(categoria);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un plato de la carta", description = "Borra permanentemente un elemento del menú por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plato eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "El plato a eliminar no existe")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        menuService.obtenerPorId(id); // Lanza excepción si no existe
        menuService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}