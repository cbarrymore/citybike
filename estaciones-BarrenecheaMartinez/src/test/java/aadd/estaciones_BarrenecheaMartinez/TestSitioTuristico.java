package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import servicio.FactoriaServicios;
import sitioTuristico.modelo.InformacionCompleta;
import sitioTuristico.modelo.SitioTuristico;
import sitioTuristico.servicio.SitiosTuristicos;

class TestSitioTuristico {

	static SitiosTuristicos servicioSitios;
	
	@BeforeAll
	static void initialize()
	{
		servicioSitios = FactoriaServicios.getServicio(SitiosTuristicos.class);
	}
	
	 @Test
	 public void pruebaMostrarSitios() {
		 BigDecimal lat =new BigDecimal(47);
		 BigDecimal lng = new BigDecimal(9);
		 Set<SitioTuristico> sitios;
		try {
			sitios = servicioSitios.obtenerSitiosInteres(lat,lng);
			for(SitioTuristico sitio : sitios)
			{
			System.out.println(sitio.getURL());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	 }
	 
	 @Test
	 public void pruebaSitiosTuristicosMostrarInfo() {
		 try
		 {
			 InformacionCompleta inf = servicioSitios.obtenerInformacionSitoInteres("Catedral_de_Murcia");
			 inf.getCategorias().stream().forEach(System.out::println);
			 inf.getInfoComplementaria().stream().forEach(System.out::println);
			 System.out.println(inf.getImagen());
			 System.out.println(inf.getResumenWikipedia());
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 fail();
		 }
	 }
	 
	 @Test 
	 public void pruebaSitiosTuristicosSitioIncorrecto() {
		try
		{
			InformacionCompleta inf = servicioSitios.obtenerInformacionSitoInteres("Catedral de Murcia");
			fail();
		}
		catch (Exception e)
		{
		}
	 }

}
