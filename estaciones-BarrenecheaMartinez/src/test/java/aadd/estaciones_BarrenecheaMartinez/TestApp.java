package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioString;
import servicio.FactoriaServicios;
import sitioTuristico.modelo.InformacionCompleta;
import sitioTuristico.modelo.SitioTuristico;
import sitioTuristico.servicio.SitiosTuristicos;

public class TestApp {

	private static SitiosTuristicos servicioSitios;
	private static IServicioEstaciones servicioEstaciones;
	private static RepositorioString<SitioTuristico> repositorioSitio; 
	private static RepositorioString<Estacion> repositorioEstaciones;
	private static List<Estacion> idBorrarEstaciones;
 @BeforeAll
 public static void initialization()
 {
	 servicioSitios = FactoriaServicios.getServicio(SitiosTuristicos.class);
	 servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	 repositorioSitio = FactoriaRepositorios.getRepositorio(SitioTuristico.class);
	 repositorioEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
	 idBorrarEstaciones = new LinkedList<>();
 }
	
 @AfterAll
 public static void borrar()
 {
	 idBorrarEstaciones.stream().forEach(t -> {
		try {
			repositorioEstaciones.delete(t);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
 }
 
 @Test
 public void pruebaMostrarSitios() {
	 BigDecimal lat =new BigDecimal(47);
	 BigDecimal lng = new BigDecimal(9);
	 Set<SitioTuristico> sitios =  servicioSitios.obtenerSitiosInteres(lat,lng);
	 for(SitioTuristico sitio : sitios)
	 {
		 System.out.println(sitio.getURL());
	 }
 }
 
 @Test 
 public void pruebaSitiosTuristicosMostrarInfo() {
	 InformacionCompleta inf = servicioSitios.obtenerInformacionSitoInteres("Catedral_de_Murcia");
	 inf.getCategorias().stream().forEach(System.out::println);
	 inf.getInfoComplementaria().stream().forEach(System.out::println);
	 System.out.println(inf.getImagen());
	 System.out.println(inf.getResumenWikipedia());
 }
 
 @Test 
 public void pruebaSitiosTuristicosSitioIncorrecto() {
	 InformacionCompleta inf = servicioSitios.obtenerInformacionSitoInteres("Catedral de Murcia");
	 assertEquals(null, inf);
 }
 
 
 @Test
 public void pruebaServicioEstacionesDarAlta() {
	String idEstacion = servicioEstaciones.altaEstacion("Estacion1", 2, 30004, new BigDecimal("47"), new BigDecimal("9"));
	Estacion estacion = servicioEstaciones.obtenerEstacion(idEstacion);
	assertNotEquals(null,estacion);
	assertEquals(idEstacion, estacion.getId());
	idBorrarEstaciones.add(estacion);
 }
 
 @Test
 public void pruebaServicioEstacionesEstablecerSitiosEnEstacion() {
	String idEstacion = servicioEstaciones.altaEstacion("Estacion1", 2, 30004, new BigDecimal("47"), new BigDecimal("9"));
	Estacion estacion = servicioEstaciones.obtenerEstacion(idEstacion);
	Set<SitioTuristico> set = servicioEstaciones.obtenerSitiosTuristicosProximos(idEstacion);
	servicioEstaciones.establecerSitiosTuristicos(idEstacion, set);
	assertNotEquals(null, estacion.getSitiosInteres());
	idBorrarEstaciones.add(estacion);
 }
 
 public void pruebaServicioEstacionesSitiosNulos() {
		Estacion estacion = new Estacion("Estacion1", 2, 30004, new BigDecimal("47"), new BigDecimal("9"));
		String idEstacion = estacion.getId();
		Set<SitioTuristico> set = servicioEstaciones.obtenerSitiosTuristicosProximos(idEstacion);
		assertEquals(null, set);
	 }

}
