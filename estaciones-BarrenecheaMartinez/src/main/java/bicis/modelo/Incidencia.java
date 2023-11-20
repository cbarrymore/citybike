package bicis.modelo;

import java.time.LocalDate;

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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	@Column(name="fecha_de_alta", columnDefinition = "DATE")
	private LocalDate fechaAlta;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	
	public Incidencia(String descripcion) {
		this.fechaAlta = LocalDate.now();
		this.descripcion= descripcion;
		this.estado = Estado.PENDIENTE;
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
	
}
