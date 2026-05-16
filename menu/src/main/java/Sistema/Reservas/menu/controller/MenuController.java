package Sistema.Reservas.menu.controller;

import Sistema.Reservas.menu.dto.MenuDTO;
import Sistema.Reservas.menu.model.Menu;
import Sistema.Reservas.menu.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @GetMapping
    public List<Menu> listarTodo() {
        return menuService.obtenerTodos();
    }


    @PostMapping
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
}