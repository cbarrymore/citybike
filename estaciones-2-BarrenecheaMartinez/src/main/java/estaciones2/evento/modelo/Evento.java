package estaciones2.evento.modelo;

import java.io.Serializable;

public interface Evento extends Serializable {
    public void setIdEvento(String idEvento);
    public String getIdEvento();
}
