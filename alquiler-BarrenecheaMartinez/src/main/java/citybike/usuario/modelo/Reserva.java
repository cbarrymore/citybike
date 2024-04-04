package citybike.usuario.modelo;

import java.time.LocalDateTime;

import citybike.servicio.FactoriaServicios;
import citybike.tiempo.servicio.IServicioTiempo;

public class Reserva {
	private String id;
	private String idBici;
	private LocalDateTime creada;
	private LocalDateTime caducidad;
	
	private IServicioTiempo servTiempo;
	
	public Reserva()
	{
		servTiempo = FactoriaServicios.getServicio(IServicioTiempo.class);
	}
	
	public Reserva(String idBici, LocalDateTime creada, LocalDateTime caducidad) {
		this();
		this.idBici = idBici;
		this.creada = creada;
		this.caducidad = caducidad;
	}
	
	public boolean caducada() {
		return servTiempo.now().isAfter(caducidad);
	}

	public boolean activa() {
		return !caducada();
	}

	public String getIdBici() {
		return idBici;
	}



	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}



	public LocalDateTime getCreada() {
		return creada;
	}



	public void setCreada(LocalDateTime creada) {
		this.creada = creada;
	}



	public LocalDateTime getCaducidad() {
		return caducidad;
	}



	public void setCaducidad(LocalDateTime caducidad) {
		this.caducidad = caducidad;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}