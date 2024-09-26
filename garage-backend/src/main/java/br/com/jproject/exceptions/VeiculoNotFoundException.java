package br.com.jproject.exceptions;

public class VeiculoNotFoundException extends RuntimeException{

    public VeiculoNotFoundException(Long id) {
        super("Veículo não encontrado.");
    }

    public VeiculoNotFoundException(String message) {
        super(message);
    }
}
