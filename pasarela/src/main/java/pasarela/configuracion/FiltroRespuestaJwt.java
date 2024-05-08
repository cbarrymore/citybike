package pasarela.configuracion;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pasarela.usuarios.servicio.IServicioUsuarios;
import pasarela.usuarios.servicio.ServicioUsuariosException;

@Component
public class FiltroRespuestaJwt extends OncePerRequestFilter {


    @Autowired
	private IServicioUsuarios servicioUsuarios;
	
	private FiltroRespuestaJwt(IServicioUsuarios servicioUsuarios) {
		this.servicioUsuarios = servicioUsuarios;
	}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        if (uri.startsWith("/auth/oauth2")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(uri.startsWith("/auth/login")) {
            String[] usernameList = request.getParameterValues("username");
            String[] passwordList = request.getParameterValues("password");
            if(usernameList == null || passwordList == null || usernameList.length>1 || passwordList.length>1)
            {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        	String username = request.getParameterValues("username")[0];
            String password = request.getParameterValues("password")[0];
            try 
            {
                Map<String, Object> claims = servicioUsuarios.verificarUsuario(username, password);
                if(claims==null)
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario o contraseña incorrectos");
                sendJWT(claims, response);
            }
            catch (ServicioUsuariosException | IOException e)
            {
                e.printStackTrace();
            }
            return;
        }
        
        
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No hay token o no es correcto");
            return;
        }	
        String token = authorization.substring("Bearer ".length()).trim();
        Claims claims;
        try
        {
            claims = Jwts.parser().setSigningKey("secreto".getBytes()).parseClaimsJws(token).getBody();
        }
        catch (Exception e)
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority((String) claims.get("rol")));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);

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