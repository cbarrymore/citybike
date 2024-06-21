package estaciones2.evento.servicio;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import estaciones2.bici.modelo.Bici;
import estaciones2.bici.repositorio.RepositorioBicis;
import estaciones2.estacion.modelo.Estacion;
import estaciones2.estacion.repositorio.RepositorioEstaciones;
import estaciones2.evento.bus.PublicadorEventos;
import estaciones2.evento.modelo.Evento;
import estaciones2.evento.modelo.EventoBiciDesactivada;
import estaciones2.historico.modelo.Historico;
import estaciones2.historico.repositorio.RepositorioHistorico;
import estaciones2.repositorio.EntidadNoEncontrada;

@Service
public class ServicioEventos implements IServicioEventos {

    private PublicadorEventos publicadorEventos;
    private RepositorioBicis repositorioBicis;
    private RepositorioHistorico repositorioHistorico;
    private RepositorioEstaciones repositorioEstaciones;

    @Autowired
    public ServicioEventos(PublicadorEventos publicadorEventos, RepositorioBicis repositorioBicis, RepositorioHistorico repositorioHistorico, RepositorioEstaciones repositorioEstaciones) {
        this.publicadorEventos = publicadorEventos;
        this.repositorioBicis = repositorioBicis;
        this.repositorioHistorico = repositorioHistorico;
        this.repositorioEstaciones = repositorioEstaciones;
    }

    @Override
    public void enviarBiciDesactivada(String idBici, String motivoBaja, LocalDate fechaBaja) {
        Evento evento = new EventoBiciDesactivada(idBici, motivoBaja, fechaBaja);
        publicadorEventos.sendMessage(evento);
    }

    @Override
    public void biciAlquilada(String idBici, LocalDateTime fechaHoraAlquiler) throws EntidadNoEncontrada {
        Bici bici = repositorioBicis.findById(idBici).orElse(null);
        Historico historico = repositorioHistorico.findByIdBici(idBici);
        if (bici == null)
            return;
        // if(bici.isDisponible())
        // throw new IllegalStateException("La bici se encuentra no disponible");
        Estacion estacion = repositorioEstaciones.findById(bici.getEstacion()).orElse(null);
        estacion.retirarBici(idBici);
        
        bici.setDisponible(false);
        bici.setEstacion(null);
        
        historico.marcarSalida(fechaHoraAlquiler.toLocalDate());
        
        

        repositorioHistorico.save(historico);
        repositorioBicis.save(bici);
        repositorioEstaciones.save(estacion);
    }

    @Override
    public void biciTerminaAlquiler(String idBici, LocalDateTime fechaHoraTermino) throws EntidadNoEncontrada {
        Bici bici = repositorioBicis.findById(idBici).orElse(null);
        if (bici == null)
            return;
        bici.setDisponible(true);
        repositorioBicis.save(bici);
    }

    @Override
    public void biciReservada(String idBici, String idUsuario) throws EntidadNoEncontrada {
        Bici bici = repositorioBicis.findById(idBici).orElse(null);
        System.out.println("Bici: " + bici);
        if (bici == null)
            return;
        bici.setDisponible(false);
        repositorioBicis.save(bici);
    }

    @Override
    public void biciReservaCancelada(String idBici, String idUsuario) throws EntidadNoEncontrada {
        Bici bici = repositorioBicis.findById(idBici).orElse(null);
        if (bici == null)
            return;
        bici.setDisponible(true);
        repositorioBicis.save(bici);
    }

    @Override
    public void biciReservaExpirada(String idBici, String idUsuario) throws EntidadNoEncontrada {
        Bici bici = repositorioBicis.findById(idBici).orElse(null);
        if (bici == null)
            return;
        bici.setDisponible(true);
        repositorioBicis.save(bici);
    }

}
