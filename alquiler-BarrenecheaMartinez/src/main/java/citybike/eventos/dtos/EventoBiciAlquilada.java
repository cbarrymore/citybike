package citybike.eventos.dtos;

import java.time.LocalDateTime;

public class EventoBiciAlquilada implements Evento {
    public static String ID_EVENTO = "bicicleta-desactivada";

    private String idEvento;
    private String idBici;
    private String fechaAlquiler;

    public EventoBiciAlquilada() {

    }

    public EventoBiciAlquilada(String idBici, String fechaAlquiler) {
        this.idEvento = ID_EVENTO;
        this.idBici = idBici;
        this.fechaAlquiler = fechaAlquiler;
    }

    public String getIdBici() {
        return idBici;
    }

    public void setIdBici(String idBici) {
        this.idBici = idBici;
    }

    @Override
    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(String fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public LocalDateTime getActualFechaAlquiler() {
        return LocalDateTime.parse(fechaAlquiler);
    }

}
