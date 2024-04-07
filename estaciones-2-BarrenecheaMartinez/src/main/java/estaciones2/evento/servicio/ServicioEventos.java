package estaciones2.evento.servicio;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import estaciones2.evento.bus.PublicadorEventos;
import estaciones2.evento.modelo.Evento;
import estaciones2.evento.modelo.EventoBiciDesactivada;

@Service
public class ServicioEventos implements IServicioEventos
{

    private PublicadorEventos publicadorEventos;

    public ServicioEventos()
    {
        publicadorEventos = new PublicadorEventos();
    }

    @Override
    public void enviarBiciDesactivada(String idBici, String motivoBaja, LocalDate fechaBaja) {
        Evento evento = new EventoBiciDesactivada(idBici, motivoBaja, fechaBaja);
        publicadorEventos.sendMessage(evento);
    }
    
}
