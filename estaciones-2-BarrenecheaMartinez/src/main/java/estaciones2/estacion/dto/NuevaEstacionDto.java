package estaciones2.estacion.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.codecs.pojo.annotations.BsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creacion de una nueva estacion en el sistema")
public class NuevaEstacionDto {
	@NotNull
	@Schema(description = "Nombre de la estacion")
	private String nombre;
	@NotNull
	@Schema(description = "Numero de puestos de la estacion")
	private int numPuestos;
	@NotNull
	@Schema(description = "Direccion postal de la estacion")
	private long dirPostal;
	@BsonProperty(value = "coordenadas")
	@Schema(description = "Coordenadas de la estacion")
	private List<Double> coordenadas;
	@NotNull
	@Schema(description = "Latitud de la estacion")
	private BigDecimal latitud;
	@NotNull
	@Schema(description = "Longitud de la estacion")
	private BigDecimal longitud;
	@Schema(description = "Fecha de alta de la estacion")
	private LocalDate fechaAlta;

	public NuevaEstacionDto() {

	}

	public NuevaEstacionDto(String nombre, int numPuestos, long dirPostal, BigDecimal latitud, BigDecimal longitud) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.fechaAlta = LocalDate.now();
		this.coordenadas = new ArrayList<Double>();
		coordenadas.add(longitud.doubleValue());
		coordenadas.add(latitud.doubleValue());
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

}
