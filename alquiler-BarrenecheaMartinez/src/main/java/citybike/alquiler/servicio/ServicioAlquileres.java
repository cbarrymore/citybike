package citybike.alquiler.servicio;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import citybike.alquiler.modelo.Alquiler;
import citybike.estaciones.servicio.IServicioEstaciones;
import citybike.estaciones.servicio.ServicioEstacionesException;
import citybike.eventos.dtos.Evento;
import citybike.eventos.dtos.EventoBiciAlquilada;
import citybike.eventos.dtos.EventoBiciAlquilerConcluido;
import citybike.eventos.dtos.EventoBiciReservaCancelada;
import citybike.eventos.dtos.EventoBiciReservaExpirada;
import citybike.eventos.dtos.EventoBiciReservada;
import citybike.eventos.servicio.IServicioEventos;
import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.FactoriaRepositorios;
import citybike.repositorio.Repositorio;
import citybike.repositorio.RepositorioException;
import citybike.servicio.FactoriaServicios;
import citybike.tiempo.servicio.IServicioTiempo;
import citybike.usuario.modelo.Reserva;
import citybike.usuario.modelo.Usuario;

public class ServicioAlquileres implements IServicioAlquileres {

	private Repositorio<Usuario, String> repositorioUsuario = FactoriaRepositorios.getRepositorio(Usuario.class);
	IServicioTiempo tiempo = FactoriaServicios.getServicio(IServicioTiempo.class);
	IServicioEstaciones estaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

	IServicioEventos servicioEventos; 

	@Override
	public void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException{
		Usuario u = procesarUsuario(idUsuario);
		if (u.reservaActiva() != null) {
			Reserva r = u.reservaActiva();
			if(!r.activa()) {
				u.getReservas().remove(u.getReservas().size() - 1);
				repositorioUsuario.update(u);
				Evento eventoReservaExpirada = new EventoBiciReservaExpirada(idUsuario, idBici);
				try {
					getServicioEventos().publicarEvento(eventoReservaExpirada);
					System.out.println("La reserva "+ r.getId() + " ha expirado");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new InternalError("Error al publicar evento");
				}
			}else {				
				throw new IllegalStateException("Ya hay una reserva activa");
			}
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
		if (!estaciones.biciDisponible(idBici)) {
			throw new IllegalStateException("La bici no está disponible");
		}
		
		LocalDateTime creada = tiempo.now();
		
		
		Reserva r = new Reserva(idBici, creada, creada.plusMinutes(30));
		u.getReservas().add(r);
		repositorioUsuario.update(u);
		
		Evento eventoReserva = new EventoBiciReservada(idUsuario, idBici);
		try {
			getServicioEventos().publicarEvento(eventoReserva);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new InternalError("Error al publicar evento");
		}

	}

	@Override
	public void confirmarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = procesarUsuario(idUsuario);
		Reserva r = u.reservaActiva();
		if (r != null) {
			if(!r.activa()) {
				u.getReservas().remove(u.getReservas().size() - 1);
				repositorioUsuario.update(u);
				
				Evento eventoReservaExpirada = new EventoBiciReservaExpirada(idUsuario, r.getIdBici());
				try {
					getServicioEventos().publicarEvento(eventoReservaExpirada);
					throw new IllegalStateException("La reserva no está activa");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new InternalError("Error al publicar evento");
				}
			}
			LocalDateTime creada = tiempo.now();
			Alquiler al = new Alquiler(r.getIdBici(), creada);
			u.getAlquileres().add(al);
			u.getReservas().remove(u.getReservas().size() - 1);
			repositorioUsuario.update(u);
			
			Evento eventoBiciAlquilada = new EventoBiciAlquilada(r.getIdBici(), creada.toString());

			try {
				getServicioEventos().publicarEvento(eventoBiciAlquilada);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new InternalError("Error al publicar evento");
			}
			
		} else
			
			throw new IllegalStateException("No hay ninguna reserva activa");

	}
	
	@Override
	public void cancelarReserva(String idUsuario) throws RepositorioException, EntidadNoEncontrada {
		Usuario u = procesarUsuario(idUsuario);
		Reserva r = u.reservaActiva();
		if (r != null) {
			if(!r.activa()) {
				Evento eventoReservaExpirada = new EventoBiciReservaExpirada(idUsuario, r.getIdBici());
				u.getReservas().remove(u.getReservas().size() - 1);
				repositorioUsuario.update(u);
				try {
					getServicioEventos().publicarEvento(eventoReservaExpirada);
					throw new IllegalStateException("La reserva no está activa");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new InternalError("Error al publicar evento");
				}
			}
			else {				
				u.getReservas().remove(u.getReservas().size() - 1);
				repositorioUsuario.update(u);
				Evento eventoReservaCancelada = new EventoBiciReservaCancelada(idUsuario, r.getIdBici());
				try {
					getServicioEventos().publicarEvento(eventoReservaCancelada);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new InternalError("Error al publicar evento");
				}
			}
		} else
			throw new IllegalStateException("No hay ninguna reserva activa o el tiempo de reserva ha expirado");
		
		
	
	}

	@Override
	public void alquilar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException {
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
		
		if(!estaciones.biciDisponible(idBici)) {
			throw new IllegalStateException("La bici no está disponible");
		}
		
		
		LocalDateTime creada = tiempo.now();
		Alquiler al = new Alquiler(idBici, creada);
		u.getAlquileres().add(al);
		repositorioUsuario.update(u);

		Evento eventoBiciAlquilada = new EventoBiciAlquilada(idBici, creada.toString());

		try {
			getServicioEventos().publicarEvento(eventoBiciAlquilada);
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
			estaciones.estacionarBici(a.getIdBici(), idEstacion);
			a.setFin(tiempo.now());
			repositorioUsuario.update(u);

			Evento evento = new EventoBiciAlquilerConcluido(a.getIdBici(), a.getFin().toString());
			getServicioEventos().publicarEvento(evento);

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
	
	private IServicioEventos getServicioEventos() {
		if(this.servicioEventos == null) {
			this.servicioEventos= FactoriaServicios.getServicio(IServicioEventos.class);
			System.out.println("El SERVICIO DE EVENTOS" + this.servicioEventos + "\n\\n\\n\\n\\n");
		}
		return this.servicioEventos;
	}
}
