package citybike.web.incidencia;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import incidencias.servicio.ServicioIncidencias;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named 
@ViewScoped
public class CrearIncidenciaWeb implements Serializable{

	private ServicioIncidencias servicioIncidencias;
	private String idBici;
	private String descripcion;
	private boolean creada;
	@Inject
    protected FacesContext facesContext;
	
	public CrearIncidenciaWeb() {
		servicioIncidencias = FactoriaServicios.getServicio(ServicioIncidencias.class);
	}
	
//    public void init() {
//        // Verifica si el parámetro "id" está presente en la URL
//        String idBici = facesContext.getExternalContext().getRequestParameterMap().get("idBici");
//        this.idBici = idBici;
//    }
	
	public void crear() {
		try {
			servicioIncidencias.crearIncidencia(idBici, descripcion);
			creada = true;
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
	}

	public String getIdBici() {
		return idBici;
	}

	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
