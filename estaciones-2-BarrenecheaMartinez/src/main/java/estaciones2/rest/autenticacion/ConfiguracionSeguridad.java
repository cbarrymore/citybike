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
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable().csrf().disable().authorizeRequests()
                .antMatchers("/login/oauth2/code/github").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
                        "/webjars/**")
                .permitAll()
                //.antMatchers("/api/estaciones/**")
                //.permitAll()
                .antMatchers("/**")
                .authenticated().and()
                .oauth2Login().successHandler(manegadorExito)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(this.filtroRespuesta, UsernamePasswordAuthenticationFilter.class);
    }

}