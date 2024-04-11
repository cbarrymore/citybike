package citybike.eventos.dtos;

import java.time.LocalDate;

public class EventoBiciDesactivada implements Evento {

    private String idEvento;
    private String idBici;
    private String motivoBaja;
    private String fechaBaja;

    public static String ID_EVENTO = "bicicleta-desactivada";

    public EventoBiciDesactivada() {

    }

    public EventoBiciDesactivada(String idBici, String motivoBaja, String fechaBaja) {
        this.idEvento = ID_EVENTO;
        this.idBici = idBici;
        this.motivoBaja = motivoBaja;
        this.fechaBaja = fechaBaja;
    }

    public String getIdBici() {
        return idBici;
    }

    public void setIdBici(String idBici) {
        this.idBici = idBici;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    @Override
    public String getIdEvento() {
        return idEvento;
    }

}
