package estaciones2.evento.servicio;

import java.time.LocalDate;

public interface IServicioEventos {
    
    public void enviarBiciDesactivada(String idBici, String motivoBaja, LocalDate fechaBaja);
}
