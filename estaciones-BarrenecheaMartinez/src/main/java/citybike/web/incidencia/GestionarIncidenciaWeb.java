package citybike.web.incidencia;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DialogFrameworkOptions;

import bicis.dto.IncidenciaDTO;
import bicis.modelo.Estado;
import citybike.web.bici.VerIncidenciaWeb;
import estaciones.servicio.IServicioEstaciones;
import incidencias.servicio.ServicioIncidencias;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class GestionarIncidenciaWeb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ServicioIncidencias servicioIncidencias;
	private IncidenciaDTO incidencia;
	private String idBici;
	private String idIncidencia;
	private String motivo;
	@Inject
    protected FacesContext facesContext;
	
	public GestionarIncidenciaWeb()
	{
		servicioIncidencias = FactoriaServicios.getServicio(ServicioIncidencias.class);
	}
	
	
	
	public ServicioIncidencias getServicioIncidencias() {
		return servicioIncidencias;
	}



	public void setServicioIncidencias(ServicioIncidencias servicioIncidencias) {
		this.servicioIncidencias = servicioIncidencias;
	}



	public IncidenciaDTO getIncidencia() {
		return incidencia;
	}



	public void setIncidencia(IncidenciaDTO incidencia) {
		this.incidencia = incidencia;
	}



	public String getIdBici() {
		return idBici;
	}



	public void setIdBici(String idBici) {
		this.idBici = idBici;
		actualizar();
	}



	public String getIdIncidencia() {
		return idIncidencia;
	}



	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}



	public String getMotivo() {
		return motivo;
	}



	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public boolean isAsignada()
	{
		return incidencia.getEstado().equals(Estado.ASIGNADA.toString());
	}
	
	public void actualizar()
	{
		try {
			incidencia = servicioIncidencias.recuperarIncidenciaAbiertaDTO(idBici);
			idIncidencia = incidencia.getId();
		} catch (RepositorioException | EntidadNoEncontrada e) {
			//Gestionar que no se ha podido acceder
			e.printStackTrace();
		}
	}
	
	public void cerrarIncidencia()
	{
		volver();
	}
	
	public void verAsignarIncidencia()
	{
		volver();
	}
	
	public void marcarArregladaIncidencia()
	{
		volver();
	}
	
	public void darBajaIncidencia()
	{
		volver();
	}
	
	private void volver()
	{
		try {
			facesContext.getExternalContext().redirect("/bici/verIncidenciasAbiertas.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
