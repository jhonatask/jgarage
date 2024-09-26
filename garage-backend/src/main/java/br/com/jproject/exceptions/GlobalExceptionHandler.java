package br.com.jproject.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof ConstraintViolationException) {
            return handleValidationException((ConstraintViolationException) exception);
        } else if (exception instanceof VeiculoNotFoundException) {
            return handleVeiculoNotFoundException((VeiculoNotFoundException) exception);
        } else if (exception instanceof MarcaInvalidaException) {
            return handleMarcaInvalidaException((MarcaInvalidaException) exception);
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Um erro interno ocorreu.")
                .build();
    }

    private Response handleValidationException(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
    }

    private Response handleVeiculoNotFoundException(VeiculoNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .build();
    }

    private Response handleMarcaInvalidaException(MarcaInvalidaException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage())
                .build();
    }

}
