package citybike.rest.dto;

import java.util.ArrayList;
import java.util.List;


public class UsuarioDTO {
	private String id;
	private List<ReservaDTO> reservas;
	private List<AlquilerDTO> alquileres;
	
	public UsuarioDTO(String id,List<ReservaDTO> reservas, List<AlquilerDTO> alquileres) {
		this.id = id;
		this.reservas=reservas;
		this.alquileres=alquileres;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<AlquilerDTO> getAlquileres() {
		return alquileres;
	}
	public void setAlquileres(ArrayList<AlquilerDTO> alquileres) {
		this.alquileres = alquileres;
	}
	public List<ReservaDTO> getReservas() {
		return reservas;
	}
	public void setReservas(ArrayList<ReservaDTO> reservas) {
		this.reservas = reservas;
	}
}
