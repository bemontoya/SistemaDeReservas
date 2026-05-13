package com.SistemaDeReservas.msclientes.exception;

public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String email) {
        super("Ya existe un cliente registrado con el email: " + email);
    }
}
