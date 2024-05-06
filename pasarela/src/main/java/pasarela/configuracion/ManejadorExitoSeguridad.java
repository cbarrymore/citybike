package pasarela.configuracion;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ManejadorExitoSeguridad implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> claims = fetchUserInfo(usuario);
        System.out.println(usuario.getAttributes());
        if(claims != null)
        {
            Date caducidad = Date.from(Instant.now().plusSeconds(3600));
            String token = Jwts.builder()
                        .setClaims(claims)
                        .signWith(SignatureAlgorithm.HS256, "secreto".getBytes())
                        .setExpiration(caducidad)
                        .compact();
            response.getWriter().append(token);
        }
        else
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no permitido");
        }
    }

    private Map<String, Object> fetchUserInfo(DefaultOAuth2User usuario)
    {
        Map<String, Object> claims = new HashMap<>();
        System.out.println(usuario.getName());
        claims.put("sub", usuario.getName());
        claims.put("rol", "usuario");
        
        return claims;
    }
}