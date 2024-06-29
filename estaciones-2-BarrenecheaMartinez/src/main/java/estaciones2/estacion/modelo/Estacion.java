package estaciones2.estacion.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import estaciones2.repositorio.Identificable;

@Document(collection = "estaciones")
public class Estacion implements Identificable {
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)	
	@Id
	private String id;
	private String nombre;
	private int numPuestos;
	private long dirPostal;
	@BsonProperty(value = "coordenadas")
	private List<Double> coordenadas;
	private BigDecimal latitud;
	private BigDecimal longitud;
	@BsonIgnore
	private int bicisDisponibles;
	private LocalDate fechaAlta;
	private Set<String> bicisAparcadas;

	public Estacion() {

	}

	public Estacion(String nombre, int numPuestos, long dirPostal, BigDecimal latitud, BigDecimal longitud) {
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.fechaAlta = LocalDate.now();
		this.bicisAparcadas = new HashSet<String>();
		this.coordenadas = new ArrayList<>();
		coordenadas.add(longitud.doubleValue());
		coordenadas.add(latitud.doubleValue());
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
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

	public Set<String> getBicisAparcadas() {
		return bicisAparcadas;
	}

	public void setBicisAparcadas(Set<String> bicisAparcadas) {
		this.bicisAparcadas = bicisAparcadas;
	}

	public List<Double> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<Double> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public boolean lleno() {
		return numPuestos <= bicisAparcadas.size();
	}

	public boolean aparcarBici(String idBici) {
		if (!lleno()) {
			this.bicisAparcadas.add(idBici);
			return true;
		}
		return false;
	}

	public boolean retirarBici(String idBici) {
		return bicisAparcadas.remove(idBici);
	}

	public int getBicisDisponibles() {
		return bicisDisponibles;
	}

	public void setBicisDisponibles(int bicisDisponibles) {
		this.bicisDisponibles = bicisDisponibles;
	}

}
