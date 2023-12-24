package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.lang.model.util.ElementScanner14;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import bicis.modelo.Incidencia;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import incidencias.servicio.ServicioIncidencias;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import bicis.modelo.Bici;
import bicis.modelo.Estado;

@TestMethodOrder(OrderAnnotation.class)
class TestServicioIncidencias {
	private static ServicioIncidencias servicioIncidencias = FactoriaServicios.getServicio(ServicioIncidencias.class);
	private static Repositorio<Bici, String> repoBicis = FactoriaRepositorios.getRepositorio(Bici.class);
	private static IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	private static Bici bici1;
	private static Bici bici2;
	private static Bici bici3;
	private static String estacion;
	@BeforeAll
	static void setUp() throws RepositorioException, EntidadNoEncontrada {
		String biciId1,biciId2,biciId3;
		estacion = servicioEstaciones.altaEstacion("Estacion2", 10, 30008, new BigDecimal(70),new BigDecimal(70));
		biciId1=servicioEstaciones.altaBici("Bici1.1", estacion);
		biciId2=servicioEstaciones.altaBici("Bici2.1", estacion);
		biciId3=servicioEstaciones.altaBici("Bici3.1", estacion);
		bici1 = repoBicis.getById(biciId1);
		bici2 = repoBicis.getById(biciId2);
		bici3 = repoBicis.getById(biciId3);
	}
	@AfterAll
	static void end() throws RepositorioException, EntidadNoEncontrada {
		servicioEstaciones.darBajaBici(bici1.getId(), "");
		servicioEstaciones.darBajaBici(bici2.getId(), "");
	}
	
	@Test 
	void testCrearIncidenciaIdBiciNula() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.crearIncidencia(null, null));
	}
	@Test 
	void testCrearIncidenciaDescripcionNula() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.crearIncidencia(bici1.getId(), null));
	}
	@Test
	@Order(1)
	void testCrearIncidencia() throws RepositorioException, EntidadNoEncontrada {
		servicioIncidencias.crearIncidencia(bici1.getId(),"Incidencia1");
		servicioIncidencias.crearIncidencia(bici2.getId(),"Incidencia3");
		servicioIncidencias.crearIncidencia(bici3.getId(),"Incidencia3");
		bici1 = repoBicis.getById(bici1.getId());
		bici2 = repoBicis.getById(bici2.getId());
		bici3 = repoBicis.getById(bici3.getId());
		assertTrue(bici1.getUltimaIncidencia()!=null);
		assertTrue(bici2.getUltimaIncidencia()!=null);
		assertTrue(bici3.getUltimaIncidencia()!=null);
	
	}

	@Test
	@Order(2)
	void testRecuperarIncidenciasAbiertas() {
		assertEquals(3,servicioIncidencias.recuperarIncidenciasAbiertas().size());
	}
	/*
	@Test
	@Order(3)
	void testCancelarIncidenciaIdBiciNula() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.cancelarIncidencia(null, null));
	}
	@Test 
	@Order(4)
	void testCancelarIncidenciaDescripcionNula() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.cancelarIncidencia(bici1.getId(), null));
	}
	
	@Test
	@Order(5)
	void testCancelarIncidencia() throws RepositorioException, EntidadNoEncontrada {

		servicioIncidencias.cancelarIncidencia(bici1.getId(), "Cancelada incidencia de Bici1");
		assertTrue(bici1.getUltimaIncidencia().getEstado() == Estado.CANCELADO);
	
	}
	@Test
	@Order(6)
	void testCancelarIncidenciaNoPendiente() throws RepositorioException, EntidadNoEncontrada {
		bici1.getUltimaIncidencia().setEstado(Estado.RESUELTA);
		repoBicis.update(bici1);
		Assertions.assertThrows(IllegalStateException.class,
				() -> servicioIncidencias.cancelarIncidencia(bici1.getId(), ""));
	}
	
	@Test
	@Order(7)
	void testAsignarIncidenciaIdBiciNula() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.cancelarIncidencia(null, null));
	}
	@Test 
	@Order(8)
	void testAsignarIncidenciaOperarioNulo() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.cancelarIncidencia(bici2.getId(), null));
	}
	
	
	
	@Test
	@Order(9)
	void testAsignarIncidencia() throws RepositorioException, EntidadNoEncontrada {
		servicioIncidencias.asignarIncidencia(bici2.getId(), "Pedro");
		bici2 = repoBicis.getById(bici2.getId());
		assertTrue(bici2.getUltimaIncidencia().getEstado() == Estado.ASIGNADA);
	}
	
	@Test
	@Order(10)
	void testAsignarIncidenciaNoPendiente() throws RepositorioException, EntidadNoEncontrada {
		bici2.getUltimaIncidencia().setEstado(Estado.RESUELTA);
		repoBicis.update(bici2);
		Assertions.assertThrows(IllegalStateException.class,
				() -> servicioIncidencias.cancelarIncidencia(bici2.getId(), ""));
	}
	
	@Test 
	@Order(11)
	void testResolverIncidenciaIdBiciNula() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.resolverIncidencia(null, null,true));
	}
	@Test 
	@Order(12)
	void testResolverIncidenciaMotivoNulo() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> servicioIncidencias.resolverIncidencia(bici3.getId(), null,true));
	}
	
	
	@Test
	@Order(13)
	void testResolverIncidenciaBiciReparada() throws RepositorioException, EntidadNoEncontrada {
		servicioIncidencias.asignarIncidencia(bici3.getId(), "Paco");
		servicioIncidencias.resolverIncidencia(bici3.getId(), "IncidenciaReparada",true);
		bici3 = repoBicis.getById(bici3.getId());
		assertTrue(bici3.getUltimaIncidencia().getEstado() == Estado.RESUELTA);
	}
	@Test
	@Order(15)
	void testResolverIncidenciaBiciNoReparada() throws RepositorioException, EntidadNoEncontrada {
		bici3.getUltimaIncidencia().setEstado(Estado.ASIGNADA);
		repoBicis.update(bici3);
		servicioIncidencias.resolverIncidencia(bici3.getId(), "IncidenciaNoReparada",false);
		Assertions.assertThrows(EntidadNoEncontrada.class,
				() -> repoBicis.getById(bici3.getId()));	
	}
	@Test
	@Order(14)
	void testResolverIncidenciaNoAsignada() throws RepositorioException, EntidadNoEncontrada {
		Assertions.assertThrows(IllegalStateException.class,
				() -> servicioIncidencias.resolverIncidencia(bici3.getId(), "",true));
	}
	*/
}
