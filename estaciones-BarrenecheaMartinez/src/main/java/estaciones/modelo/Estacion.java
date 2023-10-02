package estaciones.modelo;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import repositorio.Identificable;

public class Estacion implements Identificable{
	private String nombre;
	private int numPuestos;
	private long dirPostal;
	private BigDecimal latitud;
	private BigDecimal longitud;
	private String id;
	private LocalDate fechaAlta;
	
	public Estacion(String nombre, int numPuestos, long dirPostal, BigDecimal latitud, BigDecimal longitud) {
		this.id=UUID.randomUUID().toString();
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.fechaAlta = LocalDate.now();
	}
	
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id= id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public long getDirPostal() {
		return dirPostal;
	}

	public void setDirPostal(long dirPostal) {
		this.dirPostal = dirPostal;
	}

	public BigDecimal getLatitud() {
		return latitud;
	}

	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	public BigDecimal getLongitud() {
		return longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
}
