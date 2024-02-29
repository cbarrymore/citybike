package rest;

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
	public Response createReserva(@PathParam("idUsuario") String idUsuario, @FormParam("idBici") String idBici) 
			throws Exception {
		servicio.reservar(idUsuario, idBici);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	@PATCH
	@Path("/usuarios/{idUsuario}/reservas/")
	public Response updateReserva(@PathParam("idUsuario") String idUsuario) 
			throws Exception {
		servicio.confirmarReserva(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}
	@POST
	@Path("/usuarios/{idUsuario}")
	public Response createAlquiler(@PathParam("idUsuario") String idUsuario, @FormParam("idBici") String idBici) 
		throws Exception{
		servicio.alquilar(idUsuario, idBici);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	@GET
	@Path("/usuarios/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("idUsuario") String idUsuario) 
		throws Exception {
		System.out.println("Obteniendo usuario " + idUsuario);
		Usuario u = servicio.historialUsuario(idUsuario);
		System.out.println(u.getId());
		return Response.status(Response.Status.OK)
				.entity(u).build();
	}
	
	@DELETE
	@Path("usuarios/{idUsuario}/reservas/bloqueadas")
	public Response deleteReservasBloqueadas(@PathParam("idUsuario") String idUsuario)
		throws Exception {
		servicio.liberarBloqueo(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@POST
	@Path("usuarios/{idUsuario}/estaciones/{idEstacion}")
	public Response dejarBicicleta(@PathParam("idUsuario") String idUsuario, @PathParam("idEstacion") String idEstacion) 
		throws Exception {
		servicio.dejarBicicleta(idUsuario, idEstacion);
		return Response.status(Response.Status.NO_CONTENT).build();
 
	}
	
	
	
}
