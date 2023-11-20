package historicos.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorio.Identificable;

public class Historico implements Identificable{
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID) 
	private String id;
	private String bici;
	private List<Registro> registros;
	
	public Historico(String bici, List<Registro> registros) {
		this.bici=bici;
		this.registros = registros;
	}
	
	public Historico(String bici) {
		this(bici, new LinkedList<Registro>());
	}
	
	public Historico() {
		// TODO Auto-generated constructor stub
	}
	
	public String getBici() {
		return bici;
	}
	public void setBici(String bici) {
		this.bici = bici;
	}
	public List<Registro> getRegistros() {
		return registros;
	}
	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	
	public Registro getUltimoRegistro()
	{
		return registros.get(registros.size()-1);
	}
	
	public boolean biciAparcada()
	{
		return getUltimoRegistro().biciAparcada();
	}
	
	public void marcarEntrada(String estacion, LocalDate date)
	{
		registros.add(new Registro(estacion, date));
	}
	
	public void marcarEntrada(String estacion)
	{
		marcarEntrada(estacion, LocalDate.now());
	}
	
	public void marcarSalida(LocalDate date)
	{
		getUltimoRegistro().setFechaFin(date);
	}
	
	public void marcarSalida()
	{
		marcarSalida(LocalDate.now());
	}
}
