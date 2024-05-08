package citybike.rest;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.crypto.KeyGenerator;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import citybike.utils.PropertiesReader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {
	private static String SECRET_KEY_FILE = "secret_key.properties";
	private static String ALGORITHM = "HmacSHA256";

	@Context
	private ResourceInfo resourceInfo;

	@Context
	private HttpServletRequest servletRequest;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		String path = requestContext.getUriInfo().getPath();
		// rutas públicas
		if (path.equals("auth/login") || path.equals("/otra")) {
			return; // no se controla la autorización
		}

		String authorization = requestContext.getHeaderString("Authorization");
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		} else {
			String token = authorization.substring("Bearer ".length()).trim();
			try {
				Claims claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();

				// comprobar caducidad ...
				if(claims.getExpiration().before(new Date())) {
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				}

				this.servletRequest.setAttribute("claims", claims);

				Set<String> roles = new HashSet<>(Arrays.asList(claims.get("rol", String.class).split(",")));
				// Consulta si la operación está protegida por rol
				if (resourceInfo.getResourceMethod().isAnnotationPresent(PermitAll.class)) {
					return;
				}
				if (this.resourceInfo.getResourceMethod().isAnnotationPresent(RolesAllowed.class)) {
					String[] allowedRoles = resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class).value();
					if (roles.stream().noneMatch(userRole -> Arrays.asList(allowedRoles).contains(userRole))) {
						requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
					}
				}

			} catch (Exception e) { // Error de validación
				e.printStackTrace();
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			}
		}

	}

	private static byte[] getSecretKey() {
		PropertiesReader reader;
		try {
			reader = new PropertiesReader(SECRET_KEY_FILE);
			String secret_key = reader.getProperty("secret_key");
			if (secret_key == null) {
				return KeyGenerator.getInstance(ALGORITHM).generateKey().getEncoded();
			} else
				return secret_key.getBytes();
		} catch (Exception e) {
			throw new RuntimeException("La clave secreta no se ha podido obtener");
		}

	}
}
