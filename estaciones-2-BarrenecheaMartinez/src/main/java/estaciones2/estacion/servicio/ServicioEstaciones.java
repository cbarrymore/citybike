package estaciones2.estacion.servicio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
		if (modelo == null)
			throw new IllegalArgumentException("El modelo no puede ser nulo");
		if (idEstacion == null)
			throw new IllegalArgumentException("El id de la estación no puede ser nulo");

		Estacion estacion = repoEstaciones.findById(idEstacion).orElseThrow(
				() -> new EntidadNoEncontrada("Estacion no encontrada"));
		if (estacion.lleno())
			throw new IllegalStateException("Estacion llena");

		Bici nueva = new Bici(modelo, LocalDate.now());
		nueva.setDisponible(true);
		nueva.setEstacion(idEstacion);
		String idBici = repoBicis.save(nueva).getId();

		Historico historico = new Historico(idBici);
		historico.marcarEntrada(idEstacion);
		repoHistoricos.save(historico);
		repoBicis.save(nueva);
		estacion.aparcarBici(idBici);
		repoEstaciones.save(estacion);
		return idBici;

	}

	@Override
	public void darBajaBici(String idBici, String motivo) throws RepositorioException, EntidadNoEncontrada {
		Bici bici = repoBicis.findById(idBici).orElseThrow(
				() -> new EntidadNoEncontrada("Bici no encontrada"));
		// Cambiarlo a una sola función
		bici.setFechaBaja(LocalDate.now());
		bici.setMotivoBaja(motivo);
		bici.setEstacion(null);
		bici.setDisponible(false);
		Historico historico = repoHistoricos.findByIdBici(idBici);
		Estacion estacion = repoEstaciones.findById(historico.getEstacion()).orElseThrow(
				() -> new EntidadNoEncontrada("Estacion no encontrada"));
		estacion.retirarBici(idBici);
		historico.marcarSalida();
		repoBicis.save(bici);
		repoHistoricos.save(historico);
		repoEstaciones.save(estacion);
	}

	@Override
	public List<Bici> bicisEstacion(String idEstacion)
			throws RepositorioException, EntidadNoEncontrada {
		Estacion estacion = repoEstaciones.findById(idEstacion).orElseThrow(
				() -> new EntidadNoEncontrada("Estacion no encontrada"));
		Iterable<String> it = estacion.getBicisAparcadas();

		// Page<Historico> historicosBicisEnEstacion =
		// repoHistoricos.findByEstacionId(idEstaciones, null);
		// List<String> bicisEnEstacion = new ArrayList<String>();
		// for (Historico historico : historicosBicisEnEstacion) {
		// if (historico.biciAparcada())
		// bicisEnEstacion.add(historico.getBici());
		// }
		// Iterable<Bici> itBicis = repoBicis.findAllById(bicisEnEstacion);
		// List<Bici> bicis = new ArrayList<Bici>();
		// for (Bici bici : itBicis) {
		// if (bici != null)
		// bicis.add(bici);
		// }
		// return new PageImpl<Bici>(bicis);

		Iterable<Bici> itBicis = repoBicis.findAllById(it);
		List<Bici> bicis = new ArrayList<Bici>();
		for (Bici bici : itBicis) {
			if (bici != null)
				bicis.add(bici);
		}
		return bicis;
	}

	@Override
	public Page<Bici> biciEstacionPaginado(String idEstacion, Pageable paginacion) {
		Page<Bici> bicis = repoBicis.findByEstacion(idEstacion, paginacion);
		System.out.println(bicis.getNumberOfElements());
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
	public Page<Bici> bicisEstacionLimitadoPaginado(String idEstaciones, Pageable paginacion) {
		Page<Bici> bicis = repoBicis.findByEstacionAndDisponible(idEstaciones, true, paginacion);
		return bicis;
	}

	@Override
	public List<Estacion> obtenerEstaciones() throws RepositorioException {
		List<Estacion> lista = new ArrayList<Estacion>();
		repoEstaciones.findAll().forEach(e -> lista.add(e));
		return lista;
	}

	@Override
	public Page<Estacion> obtenerEstacionesPaginado(Pageable paginacion) {
		return repoEstaciones.findAll(paginacion);
	}

	@Override
	public void estacionarBici(String idEstacion, String idBici) throws RepositorioException, EntidadNoEncontrada {
		if (idEstacion == null)
			throw new IllegalArgumentException("El id de la estación no puede ser nulo");
		if (idBici == null)
			throw new IllegalArgumentException("El id de la bici no puede ser nulo");
		Bici bici = repoBicis.findById(idBici).orElseThrow(
				() -> new EntidadNoEncontrada("Bici no encontrada"));
		Historico historico = repoHistoricos.findByIdBici(idBici);
		if (historico.biciAparcada())
			throw new IllegalStateException("La bici ya está aparcada");
		Estacion estacion = repoEstaciones.findById(idEstacion).get();
		if (estacion.lleno())
			throw new IllegalStateException("La estación está llena");
		estacion.aparcarBici(idBici);
		historico.marcarEntrada(idEstacion);
		bici.setEstacion(idEstacion);
		bici.setDisponible(true);
		repoBicis.save(bici);
		repoEstaciones.save(estacion);
		repoHistoricos.save(historico);
	}

	@Override
	public boolean huecoDisponible(String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		Estacion estacion = repoEstaciones.findById(idEstacion).orElseThrow(
				() -> new EntidadNoEncontrada("Estacion no encontrada"));
		return !estacion.lleno();
	}
}
