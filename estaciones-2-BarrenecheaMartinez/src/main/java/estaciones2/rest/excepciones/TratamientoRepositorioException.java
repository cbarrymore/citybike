package estaciones2.rest.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import estaciones2.repositorio.RepositorioException;

@ControllerAdvice
public class TratamientoRepositorioException {

    @ExceptionHandler(RepositorioException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)

    public RespuestaError manejarRepositorioException(RepositorioException ex) {
        return new RespuestaError("Internal server error", ex.getMessage());
    }

}
