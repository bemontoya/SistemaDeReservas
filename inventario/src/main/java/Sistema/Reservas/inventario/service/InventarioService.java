package Sistema.Reservas.inventario.service;

import Sistema.Reservas.inventario.model.Inventario;
import Sistema.Reservas.inventario.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {
    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> obtenerTodo() {
        log.info("Consultando el estado completo del inventario");
        return inventarioRepository.findAll();
    }

    public Inventario obtenerPorId(Long id) {
        log.info("Buscando insumo con ID: {}", id);
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con el ID: " + id));
    }

    public Inventario crearInsumo(Inventario inventario) {
        log.info("Registrando nuevo insumo en bodega: {}", inventario.getNombreIngrediente());
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizarInsumo(Inventario insumo) {
        log.info("Actualizando insumo ID: {}", insumo.getId());
        return inventarioRepository.save(insumo);
    }

    public void eliminarPorId(Long id) {
        log.warn("Eliminando permanentemente el insumo con ID: {}", id);
        inventarioRepository.deleteById(id);
    }

    public Inventario descontarStock(Long id, Integer cantidadARestar) {
        Inventario insumo = obtenerPorId(id);

        if (insumo.getCantidadActual() < cantidadARestar) {
            log.error("No hay suficiente stock de {}. Requerido: {}, Disponible: {}",
                    insumo.getNombreIngrediente(), cantidadARestar, insumo.getCantidadActual());
            throw new RuntimeException("Stock insuficiente para el insumo: " + insumo.getNombreIngrediente());
        }

        insumo.setCantidadActual(insumo.getCantidadActual() - cantidadARestar);
        log.info("Stock descontado para {}. Nuevo stock: {}", insumo.getNombreIngrediente(), insumo.getCantidadActual());

        if (insumo.getCantidadActual() <= insumo.getStockMinimo()) {
            log.warn("El insumo {} ha alcanzado niveles críticos", insumo.getNombreIngrediente());
        }

        return inventarioRepository.save(insumo);
    }

    public List<Inventario> obtenerCriticos() {
        log.info("Buscando insumos bajo el stock mínimo");
        return inventarioRepository.findInsumosCriticos();
    }
}