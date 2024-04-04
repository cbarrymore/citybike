package estaciones2.rest.autenticacion;

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
    private ManegadorExitoSeguridad manegadorExito;

    @Autowired
    private FiltroRespuestaJwt filtroRespuesta;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.httpBasic().disable().authorizeRequests()
        .antMatchers("/login/oauth2/code/github").permitAll()
        .antMatchers("**").authenticated().and()
        .oauth2Login().successHandler(manegadorExito)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(this.filtroRespuesta, UsernamePasswordAuthenticationFilter.class);
    }
    
}