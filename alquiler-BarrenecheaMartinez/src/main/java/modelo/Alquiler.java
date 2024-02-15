package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Alquiler {
	private String idBici;
	private LocalDateTime inicio;
	private LocalDateTime fin;	
	
	public Alquiler(String idBici, LocalDateTime inicio) {
		this.idBici=idBici;
		this.inicio=inicio;
		this.fin =null;
	}
	
	public int tiempo() {
		LocalDateTime tiempoFin;
		if(activo()) {
			tiempoFin = LocalDateTime.now();
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




	public boolean activo() {
		return fin.equals(null);
	}

}
