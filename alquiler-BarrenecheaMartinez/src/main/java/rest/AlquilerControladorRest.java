package rest;

import javax.annotation.security.RolesAllowed;
import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import servicio.IServicioAlquileres;

@Path("alquileres")
public class AlquilerControladorRest {

	private IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);

	@Context
	private UriInfo uriInfo;

	@Context
	private HttpServletRequest servletRequest;

	@POST
	@Path("usuarios/{idUsuario}/reservas")
	/**
	 * 
	 * curl -X POST \ 'http://localhost:8080/api/alquileres/usuarios/1/reservas' \
	 * --header 'Authorization: Bearer
	 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTgzODc2fQ.B8WIN9zXUloqpojnOnkdvZyZvVt9hSqSXkQVHX9KyJ8'
	 * \ --header 'Content-Type: application/x-www-form-urlencoded' \
	 * --data-urlencode 'idBici=1'
	 */
	@RolesAllowed({"usuario","gestor"})
	public Response createReserva(@PathParam("idUsuario") String idUsuario, @FormParam("idBici") String idBici)
			throws Exception {
		servicio.reservar(idUsuario, idBici);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@PATCH
	@Path("/usuarios/{idUsuario}/reservas/")
	/**
	 * 
	 * curl -X PATCH \ 'http://localhost:8080/api/alquileres/usuarios/1/reservas' \
	 * --header 'Authorization: Bearer
	 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTg0NDAxfQ.B_2X8OhrpO3VFFipOHBEmL9YZv_Sm13voEvITZ87Oqc'
	 */
	@RolesAllowed({"usuario","gestor"})
	public Response updateReserva(@PathParam("idUsuario") String idUsuario) throws Exception {
		servicio.confirmarReserva(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();

	}

	@POST
	@Path("/usuarios/{idUsuario}")
	/**
	 * curl -X POST \ 'http://localhost:8080/api/alquileres/usuarios/1' \ --header
	 * 'Authorization: Bearer
	 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTg0NDAxfQ.B_2X8OhrpO3VFFipOHBEmL9YZv_Sm13voEvITZ87Oqc'
	 * \ --header 'Content-Type: application/x-www-form-urlencoded' \
	 * --data-urlencode 'idBici=1'
	 */
	@RolesAllowed({"usuario","gestor"})
	public Response createAlquiler(@PathParam("idUsuario") String idUsuario, @FormParam("idBici") String idBici)
			throws Exception {
		servicio.alquilar(idUsuario, idBici);

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@GET
	@Path("/usuarios/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * curl -X GET \ 'http://localhost:8080/api/alquileres/usuarios/1' \ --header
	 * 'Authorization: Bearer
	 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTg0NDAxfQ.B_2X8OhrpO3VFFipOHBEmL9YZv_Sm13voEvITZ87Oqc'
	 * 
	 */
	@RolesAllowed({"usuario","gestor"})
	public Response getUsuario(@PathParam("idUsuario") String idUsuario) throws Exception {
		try {
			System.out.println("Obteniendo usuario " + idUsuario);
			Usuario u = servicio.historialUsuario(idUsuario);
			System.out.println(u.getId());
			Response r = Response.status(Response.Status.OK).entity(u).build();
			System.out.println(r.getEntity());
			return r;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	@DELETE
	@Path("usuarios/{idUsuario}/reservas/bloqueadas")
	@RolesAllowed("gestor")
	/**
	 * curl -X DELETE \
	 * 'http://localhost:8080/api/alquileres/usuarios/1/reservas/bloqueadas' \
	 * --header 'Authorization: Bearer
	 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTg0NDAxfQ.B_2X8OhrpO3VFFipOHBEmL9YZv_Sm13voEvITZ87Oqc'
	 * 
	 */
	public Response deleteReservasBloqueadas(@PathParam("idUsuario") String idUsuario) throws Exception {
		try{
			servicio.liberarBloqueo(idUsuario);
			return Response.status(Response.Status.NO_CONTENT).build();
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@POST
	@Path("usuarios/{idUsuario}/estaciones/{idEstacion}")
	/***
	 * 
	 * curl -X POST \ 'http://localhost:8080/api/alquileres/usuarios/1/estaciones/1'
	 * \ --header 'Authorization: Bearer
	 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTg0NDAxfQ.B_2X8OhrpO3VFFipOHBEmL9YZv_Sm13voEvITZ87Oqc'
	 * 
	 * 
	 * 
	 */
	@RolesAllowed({"usuario","gestor"})
	public Response dejarBicicleta(@PathParam("idUsuario") String idUsuario, @PathParam("idEstacion") String idEstacion)
			throws Exception {
		servicio.dejarBicicleta(idUsuario, idEstacion);
		return Response.status(Response.Status.NO_CONTENT).build();

	}

}
