package com.SistemaDeReservas.msclientes.service;

import com.SistemaDeReservas.msclientes.dto.ClienteRequestDTO;
import com.SistemaDeReservas.msclientes.dto.ClienteResponseDTO;
import com.SistemaDeReservas.msclientes.exception.ClienteNotFoundException;
import com.SistemaDeReservas.msclientes.exception.EmailDuplicadoException;
import com.SistemaDeReservas.msclientes.model.Cliente;
import com.SistemaDeReservas.msclientes.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;


    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    public List<ClienteResponseDTO> obtenerTodos() {
        logger.info("[ms-clientes] Consultando todos los clientes activos");

        List<Cliente> clientes = clienteRepository.findByActivoTrue();

        logger.info("[ms-clientes] Se encontraron {} clientes activos", clientes.size());
        return clientes.stream()
                .map(this::mapearAResponseDTO)
                .collect(Collectors.toList());
    }


    public ClienteResponseDTO obtenerPorId(Long id) {
        logger.info("[ms-clientes] Buscando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("[ms-clientes] Cliente con ID {} no encontrado", id);
                    return new ClienteNotFoundException(id);
                });

        logger.info("[ms-clientes] Cliente encontrado: {} {}", cliente.getNombre(), cliente.getApellido());
        return mapearAResponseDTO(cliente);
    }


    public ClienteResponseDTO crearCliente(ClienteRequestDTO requestDTO) {
        logger.info("[ms-clientes] Intentando crear cliente con email: {}", requestDTO.getEmail());


        if (clienteRepository.existsByEmail(requestDTO.getEmail())) {
            logger.warn("[ms-clientes] Email duplicado al crear cliente: {}", requestDTO.getEmail());
            throw new EmailDuplicadoException(requestDTO.getEmail());
        }

        Cliente nuevoCliente = Cliente.builder()
                .nombre(requestDTO.getNombre())
                .apellido(requestDTO.getApellido())
                .email(requestDTO.getEmail())
                .telefono(requestDTO.getTelefono())
                .activo(true)
                .build();

        try {
            Cliente clienteGuardado = clienteRepository.save(nuevoCliente);
            logger.info("[ms-clientes] Cliente creado exitosamente con ID: {}", clienteGuardado.getIdCliente());
            return mapearAResponseDTO(clienteGuardado);
        } catch (Exception e) {
            logger.error("[ms-clientes] Error al guardar cliente en BD: {}", e.getMessage());
            throw e;
        }
    }


    public ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO requestDTO) {
        logger.info("[ms-clientes] Actualizando cliente con ID: {}", id);

        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("[ms-clientes] No se puede actualizar: cliente con ID {} no existe", id);
                    return new ClienteNotFoundException(id);
                });


        clienteRepository.findByEmail(requestDTO.getEmail()).ifPresent(otroCliente -> {
            if (!otroCliente.getIdCliente().equals(id)) {
                logger.warn("[ms-clientes] Email {} ya pertenece a otro cliente", requestDTO.getEmail());
                throw new EmailDuplicadoException(requestDTO.getEmail());
            }
        });

        clienteExistente.setNombre(requestDTO.getNombre());
        clienteExistente.setApellido(requestDTO.getApellido());
        clienteExistente.setEmail(requestDTO.getEmail());
        clienteExistente.setTelefono(requestDTO.getTelefono());

        Cliente clienteActualizado = clienteRepository.save(clienteExistente);
        logger.info("[ms-clientes] Cliente con ID {} actualizado exitosamente", id);
        return mapearAResponseDTO(clienteActualizado);
    }


    public void desactivarCliente(Long id) {
        logger.info("[ms-clientes] Desactivando cliente con ID: {}", id);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("[ms-clientes] No se puede desactivar: cliente con ID {} no existe", id);
                    return new ClienteNotFoundException(id);
                });


        if (!cliente.getActivo()) {
            logger.warn("[ms-clientes] El cliente con ID {} ya se encuentra inactivo", id);
            throw new ClienteNotFoundException("El cliente con ID " + id + " ya se encuentra inactivo");
        }

        cliente.setActivo(false);
        clienteRepository.save(cliente);
        logger.info("[ms-clientes] Cliente con ID {} desactivado correctamente", id);
    }

    private ClienteResponseDTO mapearAResponseDTO(Cliente cliente) {
        return ClienteResponseDTO.builder()
                .idCliente(cliente.getIdCliente())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .activo(cliente.getActivo())
                .build();
    }
}
