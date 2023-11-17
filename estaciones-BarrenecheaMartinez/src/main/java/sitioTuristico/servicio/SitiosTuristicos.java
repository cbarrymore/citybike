package sitioTuristico.servicio;

import java.math.BigDecimal;
import java.util.Set;
import sitioTuristico.modelo.InformacionCompleta;
import sitioTuristico.modelo.SitioTuristico;

public interface SitiosTuristicos {
	
	public Set<SitioTuristico> obtenerSitiosInteres(BigDecimal latitud, BigDecimal longitud) throws Exception;
	
	public InformacionCompleta obtenerInformacionSitoInteres(String idSitio) throws Exception;
}
