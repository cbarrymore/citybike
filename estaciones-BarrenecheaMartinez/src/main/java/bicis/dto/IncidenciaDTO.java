package bicis.dto;

import java.time.LocalDate;

import bicis.modelo.Estado;

public class IncidenciaDTO {
	private String id;
	private LocalDate fechaAlta;
	private LocalDate fechaCierre;
	private String descripcion;
	private String estado;
	private String idBici;
	
	
	public IncidenciaDTO(String id, LocalDate fechaAlta,
			LocalDate fechaCierre,String descripcion,String estado, String idBici) {
		
		this.id=id;
		this.fechaAlta = fechaAlta;
		this.fechaCierre = fechaCierre;
		this.descripcion = descripcion;
		this.estado = estado;
		this.idBici = idBici;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public LocalDate getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getIdBici() {
		return idBici;
	}


	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}
	
}
