package estaciones2.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;

import estaciones2.estacion.modelo.Estacion;

public class EstacionDto {
	private String id;
	private String nombre;
	private int numPuestos;
	private long dirPostal;
	@BsonProperty(value="coordenadas")
	private List<Double> coordenadas;
	private BigDecimal latitud;
	private BigDecimal longitud;
	private LocalDate fechaAlta;
	
	public EstacionDto()
	{
		
	}
	
	public EstacionDto(String nombre, int numPuestos, long dirPostal, BigDecimal latitud, BigDecimal longitud) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.fechaAlta = LocalDate.now();
		this.coordenadas = List.of(longitud.doubleValue(),latitud.doubleValue());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<Double> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<Double> coordenadas) {
		this.coordenadas = coordenadas;
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
	
	public static EstacionDto deEntidad(Estacion estacion)
	{
		EstacionDto nueva =  new EstacionDto(estacion.getNombre(), estacion.getNumPuestos(), estacion.getDirPostal(), estacion.getLatitud(), 
				estacion.getLongitud());
		nueva.setId(estacion.getId());
		return nueva;
	}
	
}
