package aadd.estaciones_BarrenecheaMartinez;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sitioTuristico.servicio.SitiosTuristicosGeoNames;

public class TestApp {

 @Test
 public void prueba() {
	 SitiosTuristicosGeoNames g = new SitiosTuristicosGeoNames();
	 BigDecimal lat =new BigDecimal(47);
	 BigDecimal lng = new BigDecimal(9);
			 
	 g.obtenerSitiosInteres(lat,lng);
 }
 
 @Test 
 public void prueba2() {
	 SitiosTuristicosGeoNames g = new SitiosTuristicosGeoNames();
	 
	 g.obtenerInformacionSiitoInteres("Catedral_de_Murcia");
 }

}
