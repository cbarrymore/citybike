package citybike.alquiler.servicio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import citybike.alquiler.modelo.Alquiler;
import citybike.eventos.servicio.EventosEmitir;
import citybike.eventos.servicio.IServicioEventos;
import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.FactoriaRepositorios;
import citybike.repositorio.Repositorio;
import citybike.repositorio.RepositorioException;
import citybike.servicio.FactoriaServicios;
import citybike.servicio.IServicioEstaciones;
import citybike.servicio.ServicioEstacionesException;
import citybike.tiempo.servicio.IServicioTiempo;
import citybike.usuario.modelo.Reserva;
import citybike.usuario.modelo.Usuario;

public class ServicioAlquileres implements IServicioAlquileres {

	private Repositorio<Usuario, String> repositorioUsuario = FactoriaRepositorios.getRepositorio(Usuario.class);
	IServicioTiempo tiempo = FactoriaServicios.getServicio(IServicioTiempo.class);
	IServicioEstaciones estaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

	IServicioEventos servicioEventos = FactoriaServicios.getServicio(IServicioEventos.class);

	@Override
	public void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = procesarUsuario(idUsuario);
		if (u.reservaActiva() != null) {
			throw new IllegalStateException("Ya hay una reserva activa");
		}
		if (u.alquilerActivo() != null) {
			throw new IllegalStateException("Ya hay un alquiler activo");
		}
		if (u.bloqueado()) {
			throw new IllegalStateException("El usuario está bloqueado");
		}
		if (u.superaTiempo()) {
			throw new IllegalStateException("El usuario ha superado el tiempo de uso");
		}

		LocalDateTime creada = tiempo.now();
		Reserva r = new Reserva(idBici, creada, creada.plusMinutes(30));
		u.getReservas().add(r);
		repositorioUsuario.update(u);

	}

	@Override
	public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = procesarUsuario(idUsuario);
		Reserva r = u.reservaActiva();
		if (r != null) {
			Alquiler al = new Alquiler(r.getIdBici(), tiempo.now());
			u.getAlquileres().add(al);
			u.getReservas().remove(u.getReservas().size() - 1);
			repositorioUsuario.update(u);
		} else
			throw new IllegalStateException("No hay ninguna reserva activa");

	}

	@Override
	public void alquilar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = procesarUsuario(idUsuario);

		if (u.reservaActiva() != null) {
			throw new IllegalStateException("Para alquilar, debe no haber una reserva activa");
		}
		if (u.alquilerActivo() != null) {
			throw new IllegalStateException("Ya hay un alquiler activo");
		}
		if (u.bloqueado()) {
			throw new IllegalStateException("El usuario está bloqueado");
		}
		if (u.superaTiempo()) {
			throw new IllegalStateException("El usuario ha superado el tiempo de uso");
		}
		LocalDateTime creada = tiempo.now();
		Alquiler al = new Alquiler(idBici, creada);
		u.getAlquileres().add(al);
		repositorioUsuario.update(u);

		String eventInfo = "{ \"idBici\": \"" + idBici + "\", \"fecha-creacion\": \"" + creada.toString() + "\" }";

		try {
			servicioEventos.publicarEvento("citybike.estaciones2", EventosEmitir.BICICLETA_ALQUILADA, eventInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new InternalError("Error al publicar evento");
		}

	}

	@Override
	public Usuario historialUsuario(String idUsuario) throws RepositorioException {
		Usuario u = procesarUsuario(idUsuario);
		return u;

	}

	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion)
			throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException {
		Usuario u = procesarUsuario(idUsuario);
		Alquiler a = u.alquilerActivo();
		if (a == null)
			throw new IllegalStateException("No hay alquiler activo");
		if (estaciones.huecoDisponible(idEstacion)) {
			estaciones.estacionarBici(idUsuario, idEstacion);
			a.setFin(tiempo.now());
			repositorioUsuario.update(u);

			String eventInfo = "{ \"idBici\": \"" + a.getIdBici() + "\", \"fecha-creacion\": \""
					+ a.getInicio().toString()
					+ "\", \"fecha-fin\": \"" + a.getFin().toString() + "\" }";

			servicioEventos.publicarEvento("citybike.alquiler", EventosEmitir.BICICLETA_ALQUILER_CONCLUIDO,
					eventInfo);

		}
	}

	@Override
	public void eliminarReservaDeBici(String idBici) throws RepositorioException, EntidadNoEncontrada {
		List<Usuario> usuarios = repositorioUsuario.getAll();
		for (Usuario u : usuarios) {
			List<Reserva> reservas = u.getReservas().stream().filter(r -> r.getIdBici().equals(idBici))
					.collect(Collectors.toList());
			u.getReservas().removeAll(reservas);
			repositorioUsuario.update(u);
		}
	}

	@Override
	public void liberarBloqueo(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = procesarUsuario(idUsuario);
		List<Reserva> caducadas = u.getReservas().stream().filter(r -> r.caducada()).collect(Collectors.toList());
		u.getReservas().removeAll(caducadas);
		repositorioUsuario.update(u);
	}

	private Usuario procesarUsuario(String idUsuario) throws RepositorioException {
		Usuario u;
		try {
			u = repositorioUsuario.getById(idUsuario);
		} catch (EntidadNoEncontrada e) {
			u = new Usuario(idUsuario);
			repositorioUsuario.add(u);
		}
		return u;
	}
}
