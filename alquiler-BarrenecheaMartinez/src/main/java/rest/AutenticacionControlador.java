package rest;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import servicio.FactoriaServicios;
import servicio.IServicioAlquileres;
import servicio.IServicioUsuarios;
import utils.PropertiesReader;

@Path("/auth")
public class AutenticacionControlador {
	public IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);
	private static String SECRET_KEY_FILE = "secret_key.properties";
	private static String ALGORITHM = "HmacSHA256";
	private static byte[] SECRET_KEY = getSecretKey();

	@POST
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		Map<String, Object> claims = servicio.verificarCredenciales(username, password);
		if (claims != null) {
			Date caducidad = Date.from(Instant.now().plusSeconds(3600)); // 1 hora de validez
			String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
					.setExpiration(caducidad).compact();
			return Response.ok(token).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
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
			// TODO Auto-generated catch block
			throw new RuntimeException("La clave secreta no se ha podido obtener");
		}

	}
}
