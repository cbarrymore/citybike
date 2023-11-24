package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bicis.modelo.Bici;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import historicos.modelo.Historico;
import historicos.repositorio.FiltroBusquedaHistorico;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

class TestServicioEstaciones {

	private static IServicioEstaciones servicioEstaciones;
	private static Repositorio<Estacion, String> repoEstaciones;
	private static Repositorio<Bici, String> repoBicis;
	private static Repositorio<Historico, String> repoHistoricos;
	private FiltroBusquedaHistorico filtroHistorico 
	private static Estacion estacion1;
	private static Bici bici;
	@BeforeAll
	void initialize()
	{
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		repoEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
		repoBicis = FactoriaRepositorios.getRepositorio(Bici.class);
		repoHistoricos = FactoriaRepositorios.getRepositorio(Historico.class);
		filtroHistorico = FactoriaRepositorios.getRepositorio(Historico.class);
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
	void testAltaBiciArgumento1() throws RepositorioException, EntidadNoEncontrada {
		Assertions.assertThrows(IllegalArgumentException.class,
				()->servicioEstaciones.altaBici(null, estacion1.getId()));
	}
	
	@Test
	void testAltaBiciArgumento2() throws RepositorioException, EntidadNoEncontrada {
		Assertions.assertThrows(IllegalArgumentException.class,
				()->servicioEstaciones.altaBici(null, estacion1.getId()));
	}
	
	@Test
	void testRetirarBici() throws RepositorioException, EntidadNoEncontrada
	{
		servicioEstaciones.retirarBici(bici.getId());
		Historico h = filtroHistorico.getByBiciId(bici.getId());
		assertFalse(h.biciAparcada());
	}
	
	@Test
	void testRetirarBiciArgumento() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalArgumentException.class,
				()->servicioEstaciones.retirarBici(null));
	}
	
	@Test
	void testRetirarBiciYaRetirada() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalStateException.class,
				()->servicioEstaciones.retirarBici(bici.getId()));
	}
	
	@Test
	void testEstacionarBici() throws RepositorioException, EntidadNoEncontrada
	{
		servicioEstaciones.estacionarBici(bici.getId());
		Historico h = filtroHistorico.getByBiciId(bici.getId());
		assertTrue(h.biciAparcada());
	}
	
	@Test
	void testEstacinarBiciArgumento() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalArgumentException.class,
				()->servicioEstaciones.estacionarBici(null));
	}
	
	@Test
	void testEstacinarBiciArgumento2() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalArgumentException.class,
				()->servicioEstaciones.estacionarBici(bici.getId(), null));
	}
	
}
