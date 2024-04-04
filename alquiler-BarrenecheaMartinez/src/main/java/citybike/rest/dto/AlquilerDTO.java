package rest.dto;

import java.time.LocalDateTime;


public class AlquilerDTO {
	private String id;
	private String idBici;
	private String inicio;
	private String fin;

	public AlquilerDTO(String id, String idBici, LocalDateTime inicio, LocalDateTime fin) {
		this.setId(id);
		this.setIdBici(idBici);
		this.inicio = inicio.toString();
		if (fin == null)
			this.fin = "";

		else
			this.fin = fin.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

}
