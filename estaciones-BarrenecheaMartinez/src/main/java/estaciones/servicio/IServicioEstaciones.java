package estaciones.servicio;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.Collection;
import java.util.Set;

import estaciones.modelo.Estacion;
import sitioTuristico.modelo.SitioTuristico;

public interface IServicioEstaciones {
	
	public String altaEstacion(String nombre,int numeroPuestos,long dirPostal,BigDecimal longitud, BigDecimal latitud);
	
	public void establecerSitiosTuristicos(String id, Set<SitioTuristico> sitiosTuristicos);
	
	public Estacion obtenerEstacion(String id);
}
