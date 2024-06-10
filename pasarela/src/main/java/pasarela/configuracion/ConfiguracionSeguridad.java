package pasarela.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private ManejadorExitoSeguridad manegadorExito;

    @Autowired
    private FiltroRespuestaJwt filtroRespuesta;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/usuarios/*", "/usuarios/solicitud/*").hasAnyAuthority("gestor")
        .antMatchers("/auth/oauth2")
        .authenticated().and()
        .oauth2Login().successHandler(manegadorExito)
        .and();
        httpSecurity.addFilterBefore(this.filtroRespuesta, UsernamePasswordAuthenticationFilter.class);
    }

}