package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import bicis.modelo.Bici;
import estaciones.repositorio.RepositorioEstacionesMongoDB;
import estaciones.servicio.IServicioEstaciones;
import estaciones.servicio.ServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

class tesBiciCerca {

	@Test
	void test() throws RepositorioException, EntidadNoEncontrada {
		IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		Set<Bici> bicis = servicioEstaciones.bicisCercanas(new BigDecimal(12), new BigDecimal(12));
		bicis.forEach(System.out::print);
	}
}
