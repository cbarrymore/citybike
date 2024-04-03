package estaciones2.rest.autenticacion;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import java.util.Map;

public class ManegadorExitoSeguridad implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> claims;
    }

}