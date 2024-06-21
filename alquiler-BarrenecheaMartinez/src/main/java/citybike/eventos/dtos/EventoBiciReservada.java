package citybike.eventos.dtos;

public class EventoBiciReservada implements Evento {
	public static String ID_EVENTO = "bicicleta-reservada";
		    
		    
	private String idEvento;
	private String idBici;
	private String idUsuario;
	
	public EventoBiciReservada() {
	}
	
	public EventoBiciReservada(String idUsuario, String idBici) {
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
}
