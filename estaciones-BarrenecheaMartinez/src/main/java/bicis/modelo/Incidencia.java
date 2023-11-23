package bicis.modelo;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "incidencia")
public class Incidencia {
	@Id
	private String id;
	@Column(name="fecha_de_alta", columnDefinition = "DATE")
	private LocalDate fechaAlta;
	
	
	@Column (name = "fecha_de_cierre",columnDefinition = "DATE")
	private LocalDate fechaCierre
	;
	@Column(name = "descripcion")
	private String descripcion;
	
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	public Incidencia() {
		// TODO Auto-generated constructor stub
	}
	
	public Incidencia(String descripcion,String idBici) {
		this.fechaAlta = LocalDate.now();
		this.descripcion= descripcion;
		this.estado = Estado.PENDIENTE;
		this.id = UUID.randomUUID().toString() + idBici;
	}
	
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
}
