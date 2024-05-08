package pasarela.configuracion;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pasarela.usuarios.servicio.IServicioUsuarios;
import pasarela.usuarios.servicio.ServicioUsuariosException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ManejadorExitoSeguridad implements AuthenticationSuccessHandler {
	@Autowired
	private IServicioUsuarios servicioUsuarios;
	
	private ManejadorExitoSeguridad(IServicioUsuarios servicioUsuarios) {
		this.servicioUsuarios = servicioUsuarios;
	}
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {
        DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
        String name = usuario.getName();
        System.out.println(usuario.getAttributes());
        String oauth2Code = Integer.toString(usuario.getAttribute("id"));
		String oauth2Username = usuario.getAttribute("login");
        Map<String, Object> claims;
		try {
			claims = servicioUsuarios.verificarUsuarioOAuth2(oauth2Code);
			if(claims == null)
	        {
	        	String codigo = servicioUsuarios.solicitarCodigo(oauth2Code);
	        	servicioUsuarios.darAlta(oauth2Code, oauth2Username, name, oauth2Code, codigo, true);
				claims = servicioUsuarios.verificarUsuarioOAuth2(oauth2Code);
	        }
			sendJWT(claims, response);
		} catch (ServicioUsuariosException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
        
    }

    private void sendJWT(Map<String,Object> claims, HttpServletResponse response) throws IOException {
    	Date caducidad = Date.from(Instant.now().plusSeconds(3600));
        String token = Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, "secreto".getBytes())
                    .setExpiration(caducidad)
                    .compact();
        response.getWriter().append(token);
    }
}