package estaciones.modelo;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.BsonBinarySubType;
import org.bson.BsonContextType;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonExtraElements;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import repositorio.Identificable;
import sitioTuristico.modelo.SitioTuristico;

public class Estacion implements Identificable{
	private String nombre;
	private int numPuestos;
	private long dirPostal;
	private List<Double> coordenadas;
	private BigDecimal latitud;
	private BigDecimal longitud;
	private String id;
	private LocalDate fechaAlta;
	private Set<SitioTuristico> sitiosInteres;
	private Set<String> bicisAparcadas;
	
	public Estacion()
	{
		
	}
	
	public Estacion(String nombre, int numPuestos, long dirPostal, BigDecimal latitud, BigDecimal longitud) {
		this.id=String.valueOf(0);
		this.nombre = nombre;
		this.numPuestos = numPuestos;
		this.dirPostal = dirPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.fechaAlta = LocalDate.now();
		this.bicisAparcadas = new HashSet<String>();
		this.coordenadas = List.of(longitud.doubleValue(),latitud.doubleValue());
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

	public Set<SitioTuristico> getSitiosInteres() {
		return sitiosInteres;
	}

	public void setSitiosInteres(Set<SitioTuristico> sitiosInteres) {
		this.sitiosInteres = sitiosInteres;
	}
	
	public Set<String> getBicisAparcadas() {
		return bicisAparcadas;
	}

	public void setBicisAparcadas(Set<String> bicisAparcadas) {
		this.bicisAparcadas = bicisAparcadas;
	}
	
	public boolean lleno()
	{
		return numPuestos<bicisAparcadas.size();
	}
	
	public boolean aparcarBici(String idBici) {
		if(!lleno())
		{
			this.bicisAparcadas.add(idBici);
			return true;
		}
		return false;
	}
	
	public boolean retirarBici(String idBici) {
		return bicisAparcadas.remove(idBici);
	}
	
}
