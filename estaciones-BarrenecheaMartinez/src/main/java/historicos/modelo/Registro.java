package historicos.modelo;

import java.time.LocalDate;

public class Registro {
	private String estacion;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	
	public Registro() {
		// TODO Auto-generated constructor stub
	}
	public Registro(String estacion, LocalDate fechaInicio,LocalDate fechaFin) {
		this.estacion = estacion;
		this.fechaFin= fechaFin;
		this.fechaInicio = fechaInicio;
	}
	
	
	public String getEstacion() {
		return estacion;
	}
	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
}
