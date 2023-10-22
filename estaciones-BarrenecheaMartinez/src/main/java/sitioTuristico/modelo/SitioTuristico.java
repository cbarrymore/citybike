package sitioTuristico.modelo;

import repositorio.Identificable;

public class SitioTuristico implements Identificable {
	private String nombre;
	private String descripcion;
	private double distanciaCoordenadas;
	private String URL;
	private InformacionCompleta infoCompleta;
	private String id;
	
	public SitioTuristico() {
	}
	
	public SitioTuristico(String nombre,String descripcion, double distanciaCoordenadas,String url,String id) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.distanciaCoordenadas = distanciaCoordenadas;
		this.URL = url;
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getDistanciaCoordenadas() {
		return distanciaCoordenadas;
	}
	public void setDistanciaCoordenadas(double distanciaCoordenadas) {
		this.distanciaCoordenadas = distanciaCoordenadas;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public InformacionCompleta getInfoCompleta() {
		return infoCompleta;
	}
	public void setInfoCompleta(InformacionCompleta infoCompleta) {
		this.infoCompleta = infoCompleta;
	}
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
		
	}
}
