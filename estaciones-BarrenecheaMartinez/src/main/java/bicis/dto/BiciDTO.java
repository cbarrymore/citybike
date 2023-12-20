package bicis.dto;

import java.time.LocalDate;

import bicis.modelo.Incidencia;

public class BiciDTO {
	private String codigo;
	private LocalDate fechaAlta;
	private String modelo;
	
	private LocalDate fechaBaja;
	private String motivoBaja;
	
	private boolean disponible;
	
	private IncidenciaDTO incidencia;
	
	public BiciDTO(String codigo, LocalDate fechaAlta, String modelo, 
			LocalDate fechaBaja,String motivoBaja, boolean disponible,IncidenciaDTO incidencia) {
		this.codigo=codigo;
		this.fechaAlta=fechaAlta;
		this.modelo = modelo;
		this.fechaBaja = fechaBaja;
		this.motivoBaja = motivoBaja;
		this.disponible = disponible;
		this.incidencia = incidencia;
	}
	
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public LocalDate getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public IncidenciaDTO getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(IncidenciaDTO incidencia) {
		this.incidencia = incidencia;
	}
}
