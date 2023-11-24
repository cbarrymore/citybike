package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bicis.modelo.Bici;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

class TestServicioEstaciones {

	private static IServicioEstaciones servicioEstaciones;
	private static Repositorio<Estacion, String> repoEstaciones;
	private static Repositorio<Bici, String> repoBicis;
	private static Estacion estacion1;
	private static Bici bici;
	@BeforeAll
	void initialize()
	{
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		repoEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
		repoBicis = FactoriaRepositorios.getRepositorio(Bici.class);
		estacion1 = new Estacion("Estacion1", 10, 30009, new BigDecimal(500), new BigDecimal(500));
		try {
			repoEstaciones.add(estacion1);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void testAltaBici() throws RepositorioException, EntidadNoEncontrada {
		String idBici = servicioEstaciones.altaBici("BMX", estacion1.getId());
		repoEstaciones.update(estacion1);
		bici = repoBicis.getById(idBici);
		assertNotEquals(null, bici);
	}
	@Test
	void testbicisCercanas() throws RepositorioException, EntidadNoEncontrada {
		Set<Bici> bicis = servicioEstaciones.bicisCercanas(new BigDecimal(12), new BigDecimal(12));
	}

}
