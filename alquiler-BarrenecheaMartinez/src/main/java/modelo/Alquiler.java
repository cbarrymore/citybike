package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.google.gson.annotations.JsonAdapter;

import servicio.FactoriaServicios;
import servicio.IServicioTiempo;
import utils.LocalDateTimeAdapter;

public class Alquiler {
	private String id;
	private String idBici;
	@JsonAdapter(value = LocalDateTimeAdapter.class)
	private LocalDateTime inicio;
	@JsonAdapter(value = LocalDateTimeAdapter.class)
	private LocalDateTime fin;

	
	private IServicioTiempo servTiempo;
	
	public Alquiler()
	{
		servTiempo = FactoriaServicios.getServicio(IServicioTiempo.class);
	}

	public Alquiler(String idBici, LocalDateTime inicio) {
		this();
		this.idBici=idBici;
		this.inicio=inicio;
		this.fin =null;
	}

	public int tiempo() {
		LocalDateTime tiempoFin;
		if(activo()) {
			tiempoFin = servTiempo.now();
		}
		else tiempoFin = fin;
		return (int)ChronoUnit.MINUTES.between(inicio, tiempoFin);
	}

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFin() {
		return fin;
	}

	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}

	public boolean deHoy()
	{
		LocalDateTime ahora = servTiempo.now();
		LocalDateTime antes = ahora.minusDays(1);
		return inicio.isBefore(ahora) && inicio.isAfter(antes);
	}
	
	public boolean deEstaSemana()
	{
		LocalDateTime ahora = servTiempo.now();
		LocalDateTime antes = ahora.minusDays(7);
		return inicio.isBefore(ahora) && inicio.isAfter(antes);
	}

	public boolean activo() {
		return fin == null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
