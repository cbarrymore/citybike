package estaciones2.evento.modelo;

public class EventoBiciReservaExpirada implements Evento{
    public static String ID_EVENTO = "bicicleta-reserva-expirada";
    
    
    private String idEvento;
    private String idBici;
    private String idUsuario;
    
    public EventoBiciReservaExpirada() {
    }

    public EventoBiciReservaExpirada(String idUsuario, String idBici) {
        this.idUsuario = idUsuario;
        this.idBici = idBici;
        this.idEvento = ID_EVENTO;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
	public void setIdEvento(String idEvento)
    {
        this.idEvento = idEvento;
    }
}
