package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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
import sitioTuristico.modelo.SitioTuristico;

@TestMethodOrder(OrderAnnotation.class)
class TestServicioEstaciones {

	private static IServicioEstaciones servicioEstaciones;
	private static Repositorio<Estacion, String> repoEstaciones;
	private static Repositorio<Bici, String> repoBicis;
	private static FiltroBusquedaHistorico filtroHistorico;
	private static Estacion estacion1;
	private static Bici bici;
	@BeforeAll
	static void initialize() throws RepositorioException, EntidadNoEncontrada
	{
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		repoEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
		List<String> lista = repoEstaciones.getIds();
		for(String id : lista)
			repoEstaciones.delete(repoEstaciones.getById(id));
		repoBicis = FactoriaRepositorios.getRepositorio(Bici.class);
		filtroHistorico = FactoriaRepositorios.getRepositorio(Historico.class);
		estacion1 = new Estacion("Estacion1", 10, 30009, new BigDecimal(30), new BigDecimal(40));
		try {
			repoEstaciones.add(estacion1);
		} catch (RepositorioException e) {
			
			e.printStackTrace();
		}
	}
	
	@AfterAll
	static void end()
	{
		
		try {
			repoBicis.delete(bici);
			repoEstaciones.delete(estacion1);
		} catch (RepositorioException | EntidadNoEncontrada e) {
			e.printStackTrace();
		}
	}
	
	 @Test
	 public void pruebaServicioEstacionesDarAlta() {
		 try
		 {
			String idEstacion = servicioEstaciones.altaEstacion("Estacion1", 2, 30004, 
					new BigDecimal("47"), new BigDecimal("9"));
			Estacion estacion = servicioEstaciones.obtenerEstacion(idEstacion);
			assertNotEquals(null,estacion);
			assertEquals(idEstacion, estacion.getId());
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 fail();
		 }
	 }
	 
	 @Test
	 public void pruebaServicioEstacionesEstablecerSitiosEnEstacion() {
		try
		{
			String idEstacion = servicioEstaciones.altaEstacion("Estacion1", 2, 30004, new BigDecimal("47"), new BigDecimal("9"));
			Set<SitioTuristico> set = servicioEstaciones.obtenerSitiosTuristicosProximos(idEstacion);
			servicioEstaciones.establecerSitiosTuristicos(idEstacion, set);
			Estacion estacion = servicioEstaciones.obtenerEstacion(idEstacion);
			assertNotEquals(null, estacion.getSitiosInteres());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail();
		}
	 }
	 
	
	@Test
	@Order(1)
	void testAltaBici() throws RepositorioException, EntidadNoEncontrada {
		String idBici = servicioEstaciones.altaBici("Montaña", estacion1.getId());
		System.out.println(idBici);
		if(idBici==null) fail();
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
	@Order(2)
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
	@Order(3)
	void testRetirarBiciYaRetirada() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalStateException.class,
				()->servicioEstaciones.retirarBici(bici.getId()));
	}
	
	@Test
	@Order(4)
	void testEstacionarBici() throws RepositorioException, EntidadNoEncontrada
	{
		servicioEstaciones.estacionarBici(bici.getId());
		Historico h = filtroHistorico.getByBiciId(bici.getId());
		assertTrue(h.biciAparcada());
	}
	
	@Test
	void testEstacionarBiciArgumento() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalArgumentException.class,
				()->servicioEstaciones.estacionarBici(null));
	}
	
	@Test
	void testEstacionarBiciArgumento2() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalArgumentException.class,
				()->servicioEstaciones.estacionarBici(bici.getId(), null));
	}
	
	@Test
	@Order(5)
	void testEstacionarBiciYaEstacionada() throws RepositorioException, EntidadNoEncontrada
	{
		Assertions.assertThrows(IllegalStateException.class,
				()->servicioEstaciones.estacionarBici(bici.getId()));
	}
	
	@Test
	void testOrdenarPorSitiosTuristicos() throws RepositorioException, EntidadNoEncontrada
	{
		String idEstacion3=servicioEstaciones.altaEstacion("estacion3sitios", 3, 30009, new BigDecimal("49.097")
				, new BigDecimal("49.097"));
		String idEstacion1=servicioEstaciones.altaEstacion("estacion1sitios", 3, 30009, new BigDecimal("49.097")
				, new BigDecimal("49.097"));
		String idEstacion2=servicioEstaciones.altaEstacion("estacion2sitios", 3, 30009, new BigDecimal("49.097")
				, new BigDecimal("49.097"));
		Set<SitioTuristico> set = new HashSet<>();
		set.add(new SitioTuristico());
		servicioEstaciones.establecerSitiosTuristicos(idEstacion1, new HashSet<SitioTuristico>(set));
		set.add(new SitioTuristico());
		servicioEstaciones.establecerSitiosTuristicos(idEstacion2, new HashSet<SitioTuristico>(set));
		set.add(new SitioTuristico());
		servicioEstaciones.establecerSitiosTuristicos(idEstacion3, set);
		
		List<Estacion> estaciones = servicioEstaciones.estacionesPorNumeroSitiosTuristicos();
		int anterior = Integer.MAX_VALUE;
		for(Estacion estacion : estaciones)
		{
			assertTrue(estacion.getSitiosInteres().size()<=anterior);
			anterior=estacion.getSitiosInteres().size();
		}
	}
	
}
