package rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TratamientoIllegalStateException implements ExceptionMapper<IllegalStateException> {
	@Override
	public Response toResponse(IllegalStateException arg0) {
		return Response.status(Response.Status.BAD_REQUEST).entity(arg0.getMessage()).build();
	}
}
