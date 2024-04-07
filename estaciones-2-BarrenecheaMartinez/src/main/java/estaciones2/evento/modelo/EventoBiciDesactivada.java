package estaciones2.evento.modelo;

import java.time.LocalDate;

public class EventoBiciDesactivada implements Evento {

    private static final String ID_EVENTO = "bicicleta-desactivada";

    private String idBici;
    private String motivoBaja;
    private String fechaBaja;


    public EventoBiciDesactivada()
    {
        
    }

    public EventoBiciDesactivada(String idBici, String motivoBaja, LocalDate fechaBaja)
    {
        this.idBici = idBici;
        this.motivoBaja = motivoBaja;
        this.fechaBaja = fechaBaja.toString();
    }

    public String getIdBici()
    {
        return idBici;
    }

    public void setIdBici(String idBici)
    {
        this.idBici = idBici;
    }

    public String getMotivoBaja()
    {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja)
    {
        this.motivoBaja = motivoBaja;
    }

    public String getFechaBaja()
    {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja)
    {
        this.fechaBaja = fechaBaja;
    }

    @Override
    public String getIdEvento()
    {
        return ID_EVENTO;
    }
    
}
