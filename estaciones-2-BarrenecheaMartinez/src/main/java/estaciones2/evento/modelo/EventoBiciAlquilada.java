package estaciones2.evento.modelo;

import java.time.LocalDateTime;

public class EventoBiciAlquilada implements Evento{
    public static String ID_EVENTO = "bicicleta-alquilada";

    private String idEvento;
    private String idBici;
    private String fechaAlquiler;

    public EventoBiciAlquilada()
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

    public String getFechaAlquiler()
    {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(String fechaAlquiler)
    {
        this.fechaAlquiler = fechaAlquiler;
    }

    public LocalDateTime getActualFechaAlquiler()
    {
        return LocalDateTime.parse(fechaAlquiler);
    }

}
