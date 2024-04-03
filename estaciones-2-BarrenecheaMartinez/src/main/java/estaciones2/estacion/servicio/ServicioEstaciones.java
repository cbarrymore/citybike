package estaciones2.estacion.servicio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import estaciones2.bici.modelo.Bici;
import estaciones2.bici.repositorio.RepositorioBicis;
import estaciones2.estacion.modelo.Estacion;
import estaciones2.estacion.repositorio.RepositorioEstaciones;
import estaciones2.historico.modelo.Historico;
import estaciones2.historico.repositorio.RepositorioHistorico;
import estaciones2.repositorio.EntidadNoEncontrada;
import estaciones2.repositorio.RepositorioException;

@Service
@Transactional
public class ServicioEstaciones implements IServicioEstaciones {

	private RepositorioEstaciones repoEstaciones;
	private RepositorioBicis repoBicis;
	private RepositorioHistorico repoHistoricos;

	@Autowired
	public ServicioEstaciones(RepositorioEstaciones repoEstaciones, RepositorioBicis repoBicis,
			RepositorioHistorico repoHistoricos) {
		this.repoEstaciones = repoEstaciones;
		this.repoBicis = repoBicis;
		this.repoHistoricos = repoHistoricos;
	}

	@Override
	public String altaEstacion(String nombre, int numeroPuestos, long dirPostal, BigDecimal longitud,
			BigDecimal latitud) throws RepositorioException {
		Estacion nueva = new Estacion(nombre, numeroPuestos, dirPostal, latitud, longitud);
		repoEstaciones.save(nueva);
		return nueva.getId();
	}

	@Override
	public Estacion obtenerEstacion(String id) throws RepositorioException, EntidadNoEncontrada {
		return repoEstaciones.findById(id).orElseThrow(
				() -> new EntidadNoEncontrada("Estacion no encontrada"));
	}

	@Override
	public String altaBici(String modelo, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		Bici nueva = new Bici(modelo, LocalDate.now());
		String id = repoBicis.save(nueva).getId();
		return id;
	}

	@Override
	public void darBajaBici(String idBici, String motivo) throws RepositorioException, EntidadNoEncontrada {
		Bici bici = repoBicis.findById(idBici).orElseThrow(
				() -> new EntidadNoEncontrada("Bici no encontrada"));
		// Cambiarlo a una sola función
		bici.setFechaBaja(LocalDate.now());
		bici.setMotivoBaja(motivo);
		Historico historico = repoHistoricos.findById(idBici).get();
		Estacion estacion = repoEstaciones.findById(historico.getEstacion()).get();
		estacion.retirarBici(idBici);
		historico.marcarSalida();
		repoBicis.save(bici);
		repoHistoricos.save(historico);
		repoEstaciones.save(estacion);
	}

	@Override
	public List<Bici> bicisEstacion(String idEstaciones) throws RepositorioException, EntidadNoEncontrada {
		Estacion estacion = repoEstaciones.findById(idEstaciones).orElseThrow(
				() -> new EntidadNoEncontrada("Estacion no encontrada"));
		Iterable<String> it = estacion.getBicisAparcadas();
		Iterable<Bici> itBicis = repoBicis.findAllById(it);
		List<Bici> bicis = new ArrayList<Bici>();
		for (Bici bici : itBicis) {
			if (bici != null)
				bicis.add(bici);
		}
		return bicis;
	}

	@Override
	public List<Bici> bicisEstacionLimitado(String idEstaciones) throws RepositorioException, EntidadNoEncontrada {
		Estacion estacion = repoEstaciones.findById(idEstaciones)
				.orElseThrow(() -> new EntidadNoEncontrada("Estacion no encontrada"));
		Iterable<String> it = estacion.getBicisAparcadas();
		Iterable<Bici> itBicis = repoBicis.findAllById(it);
		List<Bici> bicis = new ArrayList<Bici>();
		for (Bici bici : itBicis) {
			if (bici != null && bici.isDisponible())
				bicis.add(bici);
		}
		return bicis;
	}

	@Override
	public List<Estacion> obtenerEstaciones() throws RepositorioException {
		List<Estacion> lista = new ArrayList<Estacion>();
		repoEstaciones.findAll().forEach(e -> lista.add(e));
		return lista;
	}

	// @Override
	// public Page<Bici> bicisEstacionLimitadoPaginado(String idEstaciones, Pageable
	// paginacion)
	// throws RepositorioException, EntidadNoEncontrada {
	// Estacion estacion = repoEstaciones.findById(idEstaciones).get();
	// Iterable<String> it = estacion.getBicisAparcadas();
	// Iterable<Bici> itBicis = repoBicis.
	// }

}
