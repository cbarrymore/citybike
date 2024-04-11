package estaciones2.evento.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;

import estaciones2.repositorio.EntidadNoEncontrada;

public interface IServicioEventos {
    
    public void enviarBiciDesactivada(String idBici, String motivoBaja, LocalDate fechaBaja);
    public void biciAlquilada(String idBici, LocalDateTime fechaHoraAlquiler) throws EntidadNoEncontrada;
    public void biciTerminaAlquiler(String idBici, LocalDateTime fechaHoraTermino)throws EntidadNoEncontrada;

}
