package estaciones2.evento.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estaciones2.bici.modelo.Bici;
import estaciones2.bici.repositorio.RepositorioBicis;
import estaciones2.evento.bus.PublicadorEventos;
import estaciones2.evento.modelo.Evento;
import estaciones2.evento.modelo.EventoBiciDesactivada;
import estaciones2.repositorio.EntidadNoEncontrada;

@Service
public class ServicioEventos implements IServicioEventos
{

    
    private PublicadorEventos publicadorEventos;
    private RepositorioBicis repositorioBicis;

    @Autowired
    public ServicioEventos(PublicadorEventos publicadorEventos, RepositorioBicis repositorioBicis)
    {
        this.publicadorEventos = publicadorEventos;
        this.repositorioBicis = repositorioBicis;
    }

    @Override
    public void enviarBiciDesactivada(String idBici, String motivoBaja, LocalDate fechaBaja) {
        Evento evento = new EventoBiciDesactivada(idBici, motivoBaja, fechaBaja);
        publicadorEventos.sendMessage(evento);
    }

    @Override
    public void biciAlquilada(String idBici, LocalDateTime fechaHoraAlquiler) throws EntidadNoEncontrada {
        Bici bici =repositorioBicis.findById(idBici).orElseThrow(
            () -> new EntidadNoEncontrada("Bici no encontrada"));
        if(bici.isDisponible())
            throw new IllegalStateException("La bici se encuentra no disponible");
        bici.setDisponible(false);
        repositorioBicis.save(bici);
    }

    @Override
    public void biciTerminaAlquiler(String idBici, LocalDateTime fechaHoraTermino) throws EntidadNoEncontrada {
        Bici bici =repositorioBicis.findById(idBici).orElseThrow(
            () -> new EntidadNoEncontrada("Bici no encontrada"));
        if(bici.isDisponible())
            throw new IllegalStateException("La bici se encontraba ya disponible");
        bici.setDisponible(true);
        repositorioBicis.save(bici);
    }
    
}
