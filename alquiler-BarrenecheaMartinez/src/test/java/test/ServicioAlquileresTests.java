package test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Reserva;
import modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import servicio.IServicioAlquileres;
import servicio.IServicioTiempo;


public class ServicioAlquileresTests
{
	private static IServicioAlquileres servicioAlquileres;
	private static IServicioTiempo servicioTiempo;
	private static Repositorio<Usuario, String> repositorioUsuario;
	private static String idUsuario;
	
	@BeforeAll
	public static void obtenerServicio()
	{
		servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);
		servicioTiempo = FactoriaServicios.getServicio(IServicioTiempo.class);
		repositorioUsuario = FactoriaRepositorios.getRepositorio(Usuario.class);
		idUsuario = "usuarioBase";
	}
	
	@BeforeEach
	public void insertarUsuario()
	{
		try
		{
			Usuario usuario = new Usuario(idUsuario);
			repositorioUsuario.add(usuario);
		}
		catch (RepositorioException e)
		{
			e.printStackTrace();
		}
	}
	
	@AfterEach
	public void eliminarTests()
	{
		try
		{
			repositorioUsuario.delete(servicioAlquileres.historialUsuario(idUsuario));
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void testHistorialUsuarioNuevo()
	{
		String id = "nuevabicitest";
		try {
			Usuario usuario = servicioAlquileres.historialUsuario(id);
			assertEquals(usuario.getId(), id);
		} 
		catch (RepositorioException e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
	}

	@Test
	public void testReservar()
	{
		try
		{
			servicioAlquileres.reservar(idUsuario, "bici1");
			Usuario u = servicioAlquileres.historialUsuario(idUsuario);
			assertTrue(u.getReservas().size()==1);
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
		
	}

	@Test
	public void testAlquilarConReserva()
	{
		testReservar();
		assertThatExceptionOfType(IllegalStateException.class)
		.isThrownBy(()-> servicioAlquileres.alquilar("nuevabicitest", "bici1"));
	}

	@Test
	public void testReservarConReserva()
	{
		testReservar();
		assertThatExceptionOfType(IllegalStateException.class)
		.isThrownBy(()-> servicioAlquileres.reservar("nuevabicitest", "bici1"));
	}
	
	@Test
	public void testAlquilar()
	{
		try
		{
			servicioAlquileres.alquilar(idUsuario, "bici1");
			Usuario u = servicioAlquileres.historialUsuario(idUsuario);
			assertTrue(u.getAlquileres().size()==1);
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
	}
	
	@Test
	public void testAlquilarConAlquiler()
	{
		testReservar();
		assertThatExceptionOfType(IllegalStateException.class)
		.isThrownBy(()-> servicioAlquileres.reservar("nuevabicitest", "bici1"));
	}

	@Test
	public void testReservarConAlquiler()
	{
		testAlquilar();
		assertThatExceptionOfType(IllegalStateException.class)
		.isThrownBy(()-> servicioAlquileres.reservar("nuevabicitest", "bici1"));
	}
	
	
	@Test
	public void testReservarBloqueado()
	{
		try
		{
			bloquear(idUsuario);
			assertThatExceptionOfType(IllegalStateException.class)
			.isThrownBy(()-> servicioAlquileres.reservar(idUsuario, "bici4"));
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
	}
	
	@Test
	public void testAlquilarBloqueado()
	{
		try
		{
			bloquear(idUsuario);
			assertThatExceptionOfType(IllegalStateException.class)
			.isThrownBy(()-> servicioAlquileres.alquilar(idUsuario, "bici4"));
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
	}
	
	@Test
	public void testReservarSuperaTiempo()
	{
		try
		{
			superarTiempo(idUsuario);
			assertThatExceptionOfType(IllegalStateException.class)
			.isThrownBy(()-> servicioAlquileres.reservar(idUsuario, "bici1"));
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
	}
	
	@Test
	public void testAquilarSuperaTiempo()
	{
		try
		{
			superarTiempo(idUsuario);
			assertThatExceptionOfType(IllegalStateException.class)
			.isThrownBy(()-> servicioAlquileres.alquilar(idUsuario, "bici1"));
		} 
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
	}
	
	@Test
	public void testConfirmarReserva()
	{
		try
		{
			servicioAlquileres.reservar(idUsuario, "bici");
			Usuario u = servicioAlquileres.historialUsuario(idUsuario);
			int antesAlquiler = u.getAlquileres().size();
			int antesReserva = u.getReservas().size();
			servicioAlquileres.confirmarReserva(idUsuario);
			u = servicioAlquileres.historialUsuario(idUsuario);
			int despuesAlquiler = u.getAlquileres().size();
			int despuesReserva = u.getReservas().size();
			assertTrue(antesAlquiler+1 == despuesAlquiler);
			assertTrue(antesReserva == despuesReserva+1);
			
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
			fail("Excepción inesperada");
		}
	}
	
	@Test
	public void testConfirmarReservaSinReserva()
	{
		assertThatExceptionOfType(IllegalStateException.class)
		.isThrownBy(()-> servicioAlquileres.confirmarReserva(idUsuario));
	}
	
	@Test
	public void testDejarBicicleta()
	{
		try
		{
			servicioAlquileres.alquilar(idUsuario, "bici");
			servicioAlquileres.dejarBicicleta(idUsuario, "EstacionPrueba");
			Usuario u = servicioAlquileres.historialUsuario(idUsuario);
			assertEquals(null, u.alquilerActivo());
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testDejarBicicletaSinAlquiler()
	{
		assertThatExceptionOfType(IllegalStateException.class)
		.isThrownBy(()-> servicioAlquileres.dejarBicicleta(idUsuario, "EstacionPrueba"));
	}
	
	@Test
	public void testLiberarBloqueo()
	{
		try
		{
			bloquear(idUsuario);
			servicioAlquileres.liberarBloqueo(idUsuario);
			Usuario u = servicioAlquileres.historialUsuario(idUsuario);
			List<Reserva> lista = u.getReservas().stream().filter(r -> r.caducada()).toList();
			assertTrue(lista.isEmpty());
		}
		catch (RepositorioException | EntidadNoEncontrada e)
		{
			e.printStackTrace();
		}
	}
	
	private void bloquear(String id) throws RepositorioException, EntidadNoEncontrada
	{
		servicioAlquileres.reservar(id, "bici1");
		servicioTiempo.setFixedClockAt(servicioTiempo.now().plusMinutes(31));
		servicioAlquileres.reservar(id, "bici2");
		servicioTiempo.setFixedClockAt(servicioTiempo.now().plusMinutes(31));
		servicioAlquileres.reservar(id, "bici3");
		servicioTiempo.setFixedClockAt(servicioTiempo.now().plusMinutes(31));
	}
	
	private void superarTiempo(String id) throws RepositorioException, EntidadNoEncontrada
	{
		servicioAlquileres.alquilar(id, "biciSupTiempo");
		servicioTiempo.setFixedClockAt(servicioTiempo.now().plusMinutes(61));
		servicioAlquileres.dejarBicicleta(idUsuario, "EstacionPrueba");
	}
	
}