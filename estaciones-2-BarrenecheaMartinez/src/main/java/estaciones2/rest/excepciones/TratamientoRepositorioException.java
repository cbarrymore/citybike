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
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)

    public String manejarRepositorioException(RepositorioException ex) {
        return ex.getMessage();
    }

}
