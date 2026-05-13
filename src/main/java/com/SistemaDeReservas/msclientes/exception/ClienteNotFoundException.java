package com.SistemaDeReservas.msclientes.exception;


public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(Long id) {
        super("No se encontró el cliente con ID: " + id);
    }

    public ClienteNotFoundException(String mensaje) {
        super(mensaje);
    }
}
