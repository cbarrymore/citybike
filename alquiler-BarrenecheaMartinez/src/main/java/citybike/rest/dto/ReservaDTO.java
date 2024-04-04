package citybike.rest.dto;

import java.time.LocalDateTime;


public class ReservaDTO {
	
	private String id;
	private String idBici;
	
	private String creada;
	
	private String caducidad;
	
	public ReservaDTO(String id, String idBici, LocalDateTime creada, LocalDateTime caducidad) {
		this.id=id;
		this.idBici=idBici;
		this.creada=creada.toString();
		this.caducidad=caducidad.toString();
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

	public String getCreada() {
		return creada;
	}

	public void setCreada(String creada) {
		this.creada = creada;
	}

	public String getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	
}
