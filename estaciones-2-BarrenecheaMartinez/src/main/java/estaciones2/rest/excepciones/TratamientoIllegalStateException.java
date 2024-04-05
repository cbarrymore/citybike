package estaciones2.rest.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TratamientoIllegalStateException {
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public RespuestaError manejarIllegalStateException(IllegalStateException ex) {
        return new RespuestaError("Bad request", ex.getMessage());
    }
}
