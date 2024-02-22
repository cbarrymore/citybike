package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import modelo.Usuario;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import servicio.IServicioAlquileres;

@Path("usuarios")
public class AlquilerControladorRest {
	private IServicioAlquileres servicio = FactoriaServicios.getServicio(IServicioAlquileres.class);
	
	@POST
	@Path("/{idUsuario}/reservas/{idBici}")
	public Response createReserva(@PathParam("idUsuario") String idUsuario, @PathParam("idBici") String idBici) 
			throws Exception {
		servicio.reservar(idUsuario, idBici);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	@POST
	@Path("/{idUsuario}/reservas/")
	public Response updateReserva(@PathParam("idUsuario") String idUsuario) 
			throws Exception {
		servicio.confirmarReserva(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}
	@POST
	@Path("/{idUsuario}/alquileres/{idBici}")
	public Response createAlquiler(@PathParam("idUsuario") String idUsuario, @PathParam("idBici") String idBici) 
		throws Exception{
		servicio.alquilar(idUsuario, idBici);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	@GET
	@Path("/{idUsuario")
	public Response getUsuario(@PathParam("idUsuario") String idUsuario) 
		throws Exception {
		Usuario u = servicio.historialUsuario(idUsuario);
		return Response.status(Response.Status.OK)
				.entity(u).build();
	}
	
	@DELETE
	@Path("/{idUsuario}/reservas")
	public Response deleteReservasBloqueadas(@PathParam("idUsuario") String idUsuario)
		throws Exception {
		servicio.liberarBloqueo(idUsuario);
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
	
}
