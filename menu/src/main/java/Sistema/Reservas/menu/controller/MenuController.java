package Sistema.Reservas.menu.controller;

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
    public List<Menu> listarMenu() {
        return menuService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(menuService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/categoria/{categoria}")
    public List<Menu> listarPorCategoria(@PathVariable String categoria) {
        return menuService.obtenerPorCategoria(categoria);
    }

    @PostMapping
    public ResponseEntity<Menu> crear(@Valid @RequestBody Menu menu) {
        return new ResponseEntity<>(menuService.crearPlato(menu), HttpStatus.CREATED);
    }
}