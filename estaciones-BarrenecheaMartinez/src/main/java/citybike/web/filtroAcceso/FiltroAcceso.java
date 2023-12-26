package citybike.web.filtroAcceso;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = "/*",initParams = {
@WebInitParam(name = "paginasPorRol",value = "gestor=/bici/verIncidenciasAbiertas.xhtml,/incidencia/gestionarIncidencia.xhtml;"
		+ "cliente=/bici/buscarBicis.xhtml,/incidencia/crearIncidencia.xhtml")})
public class FiltroAcceso implements Filter {

    private static Map<String, List<String>> paginasPorRol;
    private static ServletContext servletContext;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Obtén la configuración del init-param
        String config = filterConfig.getInitParameter("paginasPorRol");
        servletContext = filterConfig.getServletContext();
        // Rellena el mapa de páginas a partir de la configuración
        if(config != null) {
        	paginasPorRol = parseConfig(config);
        }
        else
        	paginasPorRol = Collections.emptyMap();
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();
        if (session != null && !requestURI.equals("/index.xhtml") && !requestURI.equals("/accesoNoAutorizado.xhtml")) {
            String role = (String) session.getAttribute("role");
            List<String> paginasPermitidas = paginasPorRol.getOrDefault(role, Arrays.asList("/accesoNoAutorizado.xhtml"));

            if (paginasPermitidas.contains(requestURI) || requestURI.startsWith(httpRequest.getContextPath() + "/javax.faces.resource/")) {
                chain.doFilter(request, response);
            } else {
            	//servletContext.getRequestDispatcher(httpRequest.getContextPath() + "/accesoNoAutorizado.xhtml").forward(httpRequest, httpResponse);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/accesoNoAutorizado.xhtml");
            	return;
            }
        } else {
        	chain.doFilter(httpRequest, httpResponse);
//        	if(requestURI is "index.xhtml"º) {
//        		chain.doFilter(httpRequest, httpResponse);
//        		return;
//        	}
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/accesoNoAutorizado.xhtml");
        }
    }

    @Override
    public void destroy() {
        // Puedes realizar alguna limpieza si es necesario
    }

    private Map<String, List<String>> parseConfig(String config) {
        Map<String, List<String>> result = new HashMap<>();

        // El formato esperado podría ser algo así como "gestor=pagina1,pagina2;usuario=pagina3,pagina4"
        String[] rolesYPaginas = config.split(";");
        for (String rolYPaginas : rolesYPaginas) {
            String[] partes = rolYPaginas.split("=");
            if (partes.length == 2) {
                String rol = partes[0].trim();
                List<String> paginas = Arrays.stream(partes[1].split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

                result.put(rol, paginas);
            }
        }

        return result;
    }
}
