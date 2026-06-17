package Sistema.Reservas.menu.service;

import Sistema.Reservas.menu.model.Menu;
import Sistema.Reservas.menu.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {
    private static final Logger log = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> obtenerTodos() {
        log.info("Consultando la carta completa del menú");
        return menuRepository.findAll();
    }

    public Menu crearPlato(Menu menu) {
        log.info("Agregando nuevo plato al menú: {}", menu.getNombre());
        return menuRepository.save(menu);
    }

    public Menu obtenerPorId(Long id) {
        log.info("Buscando plato por ID: {}", id);
        return menuRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Plato con ID {} no encontrado", id);
                    return new RuntimeException("Plato no encontrado en el menú");
                });
    }

    public List<Menu> obtenerPorCategoria(String categoria) {
        log.info("Buscando platos de la categoría: {}", categoria);
        return menuRepository.findByCategoria(categoria);
    }

    // Método para actualizar un plato existente
    public Menu actualizarPlato(Menu menu) {
        log.info("Actualizando datos del plato ID [{}]: {}", menu.getId(), menu.getNombre());
        return menuRepository.save(menu); // save() hace un update automáticamente si la entidad ya tiene un ID existente
    }

    // Método para eliminar un plato por ID
    public void eliminarPorId(Long id) {
        log.info("Eliminando permanentemente el plato con ID: {}", id);
        menuRepository.deleteById(id);
    }
}