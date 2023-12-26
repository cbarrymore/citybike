package citybike.web.bici;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bicis.dto.IncidenciaDTO;
import bicis.modelo.Incidencia;
import incidencias.servicio.ServicioIncidencias;
import servicio.FactoriaServicios;

@Named 
@ViewScoped
public class VerIncidenciaWeb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ServicioIncidencias servicio;
	private List<IncidenciaDTO> incidencias;
	
	@Inject
    protected FacesContext facesContext;
	
	public VerIncidenciaWeb()
	{
		servicio = FactoriaServicios.getServicio(ServicioIncidencias.class);
		incidencias = servicio.recuperarIncidenciasAbiertasDTO();
	}
	
	public void recargarIncidencias()
	{
		incidencias = servicio.recuperarIncidenciasAbiertasDTO();
	}

	public List<IncidenciaDTO> getIncidencias()
	{
		return incidencias;
	}
	
	public void setIncidencias(List<IncidenciaDTO> incidencias)
	{
		this.incidencias = incidencias;
	}
	
	public void goToGestionarIncidencia(String idBici)
	{
		try {
			facesContext.getExternalContext().redirect("/incidencia/gestionarIncidencia.xhtml?idBici="+idBici);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
