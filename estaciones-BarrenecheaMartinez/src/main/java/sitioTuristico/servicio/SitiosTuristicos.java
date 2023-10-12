package sitioTuristico.servicio;

import java.math.BigDecimal;
import java.util.Collection;

import repositorio.Repositorio;
import repositorio.RepositorioJSON;
import repositorio.RepositorioMemoria;
import sitioTuristico.modelo.InformacionCompleta;
import sitioTuristico.modelo.SitioTuristico;

public interface SitiosTuristicos {
	
	public Collection<SitioTuristico> obtenerSitiosInteres(BigDecimal latitud, BigDecimal longitud);
	
	public InformacionCompleta obtenerInformacionSiitoInteres(String idSitio);
}
