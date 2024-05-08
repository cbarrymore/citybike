package pasarela.usuarios.servicio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TratamientoServicioUsuariosException {
    @ExceptionHandler(ServicioUsuariosException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public RespuestaError manejarEntidadNoEncontradaException(ServicioUsuariosException ex) {
        return new RespuestaError("Error en el servicio de usuarios", ex.getMessage());
    }
}
