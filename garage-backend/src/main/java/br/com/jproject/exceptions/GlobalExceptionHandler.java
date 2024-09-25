package br.com.jproject.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        if (exception != null) {
            return handleConstraintViolationException((ConstraintViolationException) exception);
        }

        if (null instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Argumento inválido", exception.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Erro Interno do Servidor", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Response handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder violations = new StringBuilder();
        exception.getConstraintViolations().forEach(violation -> {
            violations.append(violation.getPropertyPath())
                    .append(": ")
                    .append(violation.getMessage())
                    .append("; ");
        });

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Erro de Validação", violations.toString()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
