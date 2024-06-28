package citybike.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import citybike.alquiler.servicio.IServicioAlquileres;
import citybike.rest.dto.AlquilerDTO;
import citybike.rest.dto.ReservaDTO;
import citybike.rest.dto.UsuarioDTO;
import citybike.servicio.FactoriaServicios;
import citybike.usuario.modelo.Usuario;

@Path("alquileres")
public class AlquilerControladorRest {

	private IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);

	@Context
	private UriInfo uriInfo;

	@Context
	private HttpServletRequest servletRequest;

	@POST
	@Path("/usuarios/{idUsuario}/reservas")
	/**
	 * 
	 * curl -X POST \ 'http://localhost:8080/api/alquileres/usuarios/1/reservas' \
	 * --header 'Authorization: Bearer
	 * eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTgzODc2fQ.B8WIN9zXUloqpojnOnkdvZyZvVt9hSqSXkQVHX9KyJ8'
	 * \ --header 'Content-Type: application/x-www-form-urlencoded' \
	 * --data-urlencode 'idBici=1'
	 */
	public Response createReserva(@PathParam("idUsuario") String idUsuario, @QueryParam("idBici") String idBici)
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
	public Response createAlquiler(@PathParam("idUsuario") String idUsuario, @QueryParam("idBici") String idBici)
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
	public Response getUsuario(@PathParam("idUsuario") String idUsuario) throws Exception {
		try {
			System.out.println("Obteniendo usuario " + idUsuario);
			Usuario u = servicio.historialUsuario(idUsuario);
			System.out.println(u.getId());
			UsuarioDTO udto = this.transformToDto(u);
			Response r = Response.status(Response.Status.OK).entity(udto).build();
			System.out.println(r.getEntity());
			return r;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}

	@DELETE
	@Path("usuarios/{idUsuario}/reservas")
	public Response cancelarReserva(@PathParam("idUsuario") String idUsuario) throws Exception 
	{
		servicio.cancelarReserva(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}
	
	@DELETE
	@Path("usuarios/{idUsuario}/reservas/bloqueadas")
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

	@PATCH
	@Path("usuarios/{idUsuario}")
//	curl  -X PATCH \
//	  'http://localhost:8080/api/alquileres/usuarios/1' \
//	  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJ1c3VhcmlvIiwiZXhwIjoxNzA5OTg0NDAxfQ.B_2X8OhrpO3VFFipOHBEmL9YZv_Sm13voEvITZ87Oqc' \
//	  --header 'Content-Type: application/x-www-form-urlencoded' \
//	  --data-urlencode 'idEstacion=1'
	
	public Response dejarBicicleta(@PathParam("idUsuario") String idUsuario, @FormParam("idEstacion") String idEstacion)
			throws Exception {
		servicio.dejarBicicleta(idUsuario, idEstacion);
		return Response.status(Response.Status.NO_CONTENT).build();

	}

	private UsuarioDTO transformToDto(Usuario u)
	{
		List<ReservaDTO> reservas = u.getReservas().stream()
				.map(r -> new ReservaDTO(r.getId(), r.getIdBici(), r.getCreada(), r.getCaducidad())).collect(Collectors.toList());
		List<AlquilerDTO> alquileres = u.getAlquileres().stream()
				.map(a -> new AlquilerDTO(a.getId(), a.getIdBici(), a.getInicio(), a.getFin())).collect(Collectors.toList());
		return new UsuarioDTO(u.getId(), reservas, alquileres);
	}

}
