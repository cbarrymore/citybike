package bicis.dto;

import java.time.LocalDate;
import java.util.List;

import bicis.modelo.Incidencia;

public class BiciDTO {
	private String codigo;
	private LocalDate fechaAlta;
	private String modelo;
	
	private LocalDate fechaBaja;
	private String motivoBaja;
	
	private boolean disponible;
	
	private List<IncidenciaDTO> incidencias;
	
	public BiciDTO(String codigo, LocalDate fechaAlta, String modelo, 
			LocalDate fechaBaja,String motivoBaja, boolean disponible,List<IncidenciaDTO> incidencias) {
		this.codigo=codigo;
		this.fechaAlta=fechaAlta;
		this.modelo = modelo;
		this.fechaBaja = fechaBaja;
		this.motivoBaja = motivoBaja;
		this.disponible = disponible;
		this.incidencias = incidencias;
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

	public List<IncidenciaDTO> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<IncidenciaDTO> incidencias) {
		this.incidencias = incidencias;
	}
	
	public IncidenciaDTO getUltimaIncidencia()
	{
		if(incidencias.size()==0)
			return null;
		return incidencias.get(0);
	}
}
