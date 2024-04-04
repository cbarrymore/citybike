package servicio;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import modelo.Alquiler;
import modelo.Reserva;
import modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioAlquileres implements IServicioAlquileres {

	private Repositorio<Usuario, String> repositorioUsuario = FactoriaRepositorios.getRepositorio(Usuario.class);
	IServicioTiempo tiempo = FactoriaServicios.getServicio(IServicioTiempo.class);
	IServicioEstaciones estaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

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
			u.getReservas().remove(u.getReservas().size()-1);
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

		Alquiler al = new Alquiler(idBici, tiempo.now());
		u.getAlquileres().add(al);
		repositorioUsuario.update(u);
	}

	@Override
	public Usuario historialUsuario(String idUsuario) throws RepositorioException {
		Usuario u = procesarUsuario(idUsuario);
		return u;

	}

	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = procesarUsuario(idUsuario);
		Alquiler a = u.alquilerActivo();
		if(a == null)
			throw new IllegalStateException("No hay alquiler activo");
		if(estaciones.huecoDisponible(idEstacion)) {
			estaciones.estacionarBici(idUsuario, idEstacion);
			a.setFin(tiempo.now());
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
