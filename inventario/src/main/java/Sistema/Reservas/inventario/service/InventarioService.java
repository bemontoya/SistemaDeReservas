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

    public Inventario crearInsumo(Inventario inventario) {
        log.info("Registrando nuevo insumo en bodega: {}", inventario.getNombreIngrediente());
        return inventarioRepository.save(inventario);
    }

    // Lógica para descontar stock cuando se vende un plato
    public Inventario descontarStock(Long id, Integer cantidadARestar) {
        Inventario insumo = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        if (insumo.getCantidadActual() < cantidadARestar) {
            log.error("No hay suficiente stock de {}. Requerido: {}, Disponible: {}",
                    insumo.getNombreIngrediente(), cantidadARestar, insumo.getCantidadActual());
            throw new RuntimeException("Stock insuficiente para el insumo: " + insumo.getNombreIngrediente());
        }

        insumo.setCantidadActual(insumo.getCantidadActual() - cantidadARestar);
        log.info("Stock descontado para {}. Nuevo stock: {}", insumo.getNombreIngrediente(), insumo.getCantidadActual());

        // Alerta si quedó crítico
        if (insumo.getCantidadActual() <= insumo.getStockMinimo()) {
            log.warn("El insumo {} ha alcanzado niveles críticos", insumo.getNombreIngrediente());
        }

        return inventarioRepository.save(insumo);
    }

    // Obtener la lista de lo que se está acabando
    public List<Inventario> obtenerCriticos() {
        log.info("Buscando insumos bajo el stock mínimo");
        return inventarioRepository.findInsumosCriticos();
    }
}