package estaciones2.historico.modelo;

import java.time.LocalDate;


import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

public class Historico {
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID) 
	private String id;
	private String idBici;
	private String estacion;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	
	public Historico(String idBici) {
		this.idBici = idBici;
		this.estacion = null;
		this.fechaEntrada = null;
		this.fechaSalida = null;
	}
	
	public Historico() {
		// TODO Auto-generated constructor stub
	}
	
	public String getBici() {
		return idBici;
	}
	public void setBici(String bici) {
		this.idBici = bici;
	}
	
	public String getEstacion()
	{
		return estacion;
	}
	
	public void setEstacion(String estacion)
	{
		this.estacion = estacion;
	}
	
	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean biciAparcada()
	{
		return fechaSalida == null;
	}
	
	public void marcarEntrada(String estacion, LocalDate date)
	{
		this.estacion = estacion;
		this.fechaEntrada = date;
	}
	
	public void marcarEntrada(String estacion)
	{
		marcarEntrada(estacion, LocalDate.now());
	}
	
	public void marcarSalida(LocalDate date)
	{
		this.fechaSalida = date;
	}
	
	public void marcarSalida()
	{
		marcarSalida(LocalDate.now());
	}
	
}
