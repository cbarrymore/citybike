package estaciones.servicio;

import java.math.BigDecimal;
import java.util.Collection;

import estaciones.modelo.Estacion;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioMemoria;
import sitioTuristico.modelo.SitioTuristico;

public class ServicioEstaciones implements IServicioEstaciones {
	
	private  Repositorio<Estacion, String> repositorio = FactoriaRepositorios.getRepositorio(Estacion.class);
	private IServiciosTuristicos servicioTuristico = FactoriaServicios.getServicio(SitioTuristico.class);
	@Override
	public String altaEstacion(String nombre, int numeroPuestos, long dirPostal, BigDecimal longitud,
			BigDecimal latitud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void establecerSitiosTuristicos(String id, Collection<SitioTuristico> sitiosTuristicos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Estacion obtenerEstacion(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
