package estaciones2.bici.modelo;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "incidencias")
public class Incidencia {
	@Id
	private String id;
	private LocalDate fechaAlta;
	private LocalDate fechaCierre;
	private String descripcion;
	private Estado estado;
	private String idBici;

	public Incidencia() {
	}

	public Incidencia(String descripcion, String idBici) {
		this.fechaAlta = LocalDate.now();
		this.descripcion = descripcion;
		this.estado = Estado.PENDIENTE;
		this.id = UUID.randomUUID().toString() + idBici;
		this.idBici = idBici;
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

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public boolean isAbierta() {
		return this.estado.abierta();
	}

}
