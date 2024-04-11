package estaciones2.rest.autenticacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class FiltroRespuestaJwt extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        // if request path is "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
        // "/swagger-resources/**","/webjars/**" then permitAll
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURI().matches("/api/estaciones/.*/hueco"));
        if (request.getRequestURI().startsWith("/swagger-ui.html") || request.getRequestURI().startsWith("/swagger-ui")
                || request.getRequestURI().startsWith("/v3/api-docs")
                || request.getRequestURI().startsWith("/swagger-resources")
                || request.getRequestURI().startsWith("/webjars")
                || (request.getRequestURI().matches("/api/estaciones/.*/bicis/.*/aparcar") && request.getMethod().equals("PUT"))
                || request.getRequestURI().matches("/api/estaciones/.*/hueco")) {
            filterChain.doFilter(request, response);
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
            claims = Jwts.parser().setSigningKey("secreto".getBytes()).parseClaimsJws(token).getBody(); // Hay que codificarlo
        }
        catch(Exception e)
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

}
