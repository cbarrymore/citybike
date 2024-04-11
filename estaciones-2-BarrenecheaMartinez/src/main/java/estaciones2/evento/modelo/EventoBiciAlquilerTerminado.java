package estaciones2.evento.modelo;

import java.time.LocalDateTime;

public class EventoBiciAlquilerTerminado implements Evento {
    public static String ID_EVENTO = "bicicleta-alquiler-concluido";

    private String idEvento;
    private String idBici;
    private String fechaFinAlquiler;

    public EventoBiciAlquilerTerminado()
    {

    }

    public String getIdBici()
    {
        return idBici;
    }

    public void setIdBici(String idBici)
    {
        this.idBici = idBici;
    }

    @Override
    public String getIdEvento()
    {
        return idEvento;
    }

    public void setIdEvento(String idEvento)
    {
        this.idEvento = idEvento;
    }

    public String getFechaFinAlquiler()
    {
        return fechaFinAlquiler;
    }

    public void setFechaFinAlquiler(String fechaFinAlquiler)
    {
        this.fechaFinAlquiler = fechaFinAlquiler;
    }

    public LocalDateTime getActualFechaFinAlquiler()
    {
        return LocalDateTime.parse(fechaFinAlquiler);
    }
}
