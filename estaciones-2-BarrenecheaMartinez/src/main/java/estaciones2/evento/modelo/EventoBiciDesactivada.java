package estaciones2.evento.modelo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventoBiciDesactivada implements Evento {

    @JsonProperty("idEvento")
    private String idEvento;
    @JsonProperty("idBici")
    private String idBici;
    @JsonProperty("motivoBaja")
    private String motivoBaja;
    @JsonProperty("fechaBaja")
    private String fechaBaja;

    public static String ID_EVENTO = "bicicleta-desactivada";

    @JsonCreator
    public EventoBiciDesactivada()
    {
        
    }

    public EventoBiciDesactivada(String idBici, String motivoBaja, LocalDate fechaBaja)
    {
        this.idEvento = ID_EVENTO;
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
        return idEvento;
    }

    @Override
    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }
    
}
