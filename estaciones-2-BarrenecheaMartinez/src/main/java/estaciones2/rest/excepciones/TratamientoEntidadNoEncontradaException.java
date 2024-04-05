package estaciones2.rest.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import estaciones2.repositorio.EntidadNoEncontrada;

@ControllerAdvice
public class TratamientoEntidadNoEncontradaException {

    @ExceptionHandler(EntidadNoEncontrada.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public RespuestaError manejarEntidadNoEncontradaException(EntidadNoEncontrada ex) {
        return new RespuestaError("Not found", ex.getMessage());
    }
}
