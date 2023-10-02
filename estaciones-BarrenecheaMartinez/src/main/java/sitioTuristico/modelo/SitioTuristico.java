package sitioTuristico.modelo;

public abstract class SitioTuristico {
	private String nombre;
	private String descripcion;
	private long distanciaCoordenadas;
	private String URL;
	
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
	public long getDistanciaCoordenadas() {
		return distanciaCoordenadas;
	}
	public void setDistanciaCoordenadas(long distanciaCoordenadas) {
		this.distanciaCoordenadas = distanciaCoordenadas;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
}
