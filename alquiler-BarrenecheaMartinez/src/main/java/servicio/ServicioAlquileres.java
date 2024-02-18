package servicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import modelo.Alquiler;
import modelo.Reserva;
import modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioAlquileres implements IServicioAlquileres{

	private Repositorio<Usuario, String> repositorioUsuario = FactoriaRepositorios.getRepositorio(Usuario.class);
	IServicioTiempo tiempo = FactoriaServicios.getServicio(IServicioTiempo.class);
	
	@Override
	public void reservar(String idUsuario, String idBici) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		Usuario u = procesarUsuario(idUsuario);
		if(u.reservaActiva() != null  &&  u.alquilerActivo()!=null && !u.bloqueado() && !u.superaTiempo()) {
			LocalDateTime creada = tiempo.now();
			Reserva r = new Reserva(idBici,creada,creada.plusMinutes(30));
			u.getReservas().add(r);
			repositorioUsuario.update(u);
		}
		else throw new IllegalStateException("");
	}

	@Override
	public void confirmarReserva(String idUsuario) throws RepositorioException {
		Usuario u = procesarUsuario(idUsuario);
		Reserva r = u.reservaActiva();
		if (r != null) {
			Alquiler al= new Alquiler(r.getIdBici(),tiempo.now());
			u.getAlquileres().add(al);
			u.getReservas().remove(u.getReservas().size());
		}
		else
			throw new IllegalStateException();
	}

	@Override
	public void alquilar(String idUsuario, String idBici) throws RepositorioException {
		// TODO Auto-generated method stub
		Usuario u = procesarUsuario(idUsuario);
		if(u.reservaActiva() !=null && u.alquilerActivo() != null && !(u.bloqueado() || u.superaTiempo()) ) {
			Alquiler al= new Alquiler(idBici,tiempo.now());
			u.getAlquileres().add(al);
		}
		else
			throw new IllegalStateException();
	}

	@Override
	public void historialUsuario(String idUsuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dejarBicicleta(String idUsuario, String idEstacion) throws RepositorioException {
		// TODO Auto-generated method stub
		Usuario u = procesarUsuario(idUsuario);
		Alquiler a = u.alquilerActivo();
	}

	@Override
	public void liberarBloqueo(String idUsuario) throws RepositorioException {
		Usuario u = procesarUsuario(idUsuario);
		List<Reserva> caducadas= u.getReservas().stream().filter(r -> r.caducada()).collect(Collectors.toList());
		caducadas.forEach(r -> u.getReservas().remove(r));
	}
	private Usuario procesarUsuario(String idUsuario) throws RepositorioException {
		Usuario u;
		try {
			u= repositorioUsuario.getById(idUsuario);
		}  catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			u = new Usuario(idUsuario);
			repositorioUsuario.add(u);
		}
		return u;
	}
}