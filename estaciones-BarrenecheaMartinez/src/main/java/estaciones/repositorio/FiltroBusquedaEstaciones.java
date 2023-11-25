package estaciones.repositorio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import bicis.modelo.Bici;
import estaciones.modelo.Estacion;
import repositorio.RepositorioString;

public interface FiltroBusquedaEstaciones {
	public Set<Estacion> getEstacionesProximas(BigDecimal longitud, BigDecimal latitud);
	
	public Estacion getEstacionLibre();
}
