package citybike.web.incidencia;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
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
@SessionScoped
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
	private String nombre;
	private boolean faltaNombre;
	private boolean faltaMotivo;
	@Inject
    protected FacesContext facesContext;
	
	public GestionarIncidenciaWeb()
	{
		servicioIncidencias = FactoriaServicios.getServicio(ServicioIncidencias.class);
		faltaMotivo = false;
		faltaNombre = false;
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

	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public boolean isFaltaNombre() {
		return faltaNombre;
	}



	public void setFaltaNombre(boolean faltaNombre) {
		this.faltaNombre = faltaNombre;
	}



	public boolean isFaltaMotivo() {
		return faltaMotivo;
	}



	public void setFaltaMotivo(boolean faltaMotivo) {
		this.faltaMotivo = faltaMotivo;
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
		faltaNombre = false;
		boolean correcto = true;
		if(motivo == null||motivo.length() < 1)
			correcto=false;
		else
		{
			try {
				servicioIncidencias.cancelarIncidencia(idBici, motivo);
			} catch (RepositorioException | EntidadNoEncontrada e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		faltaMotivo = !correcto;
		volver(correcto);
	}
	
	public void asignarIncidencia()
	{
		faltaMotivo = false;
		boolean correcto = true;
		if(nombre == null||nombre.length() < 1)
			correcto=false;
		else
		{
			try {
				servicioIncidencias.asignarIncidencia(idBici, nombre);
			} catch (RepositorioException | EntidadNoEncontrada e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		faltaNombre = !correcto;
		volver(correcto);
	}
	
	public void marcarArregladaIncidencia()
	{
		faltaNombre = false;
		boolean correcto = true;
		if(motivo == null||motivo.length() < 1)
			correcto=false;
		else
		{
			try {
				servicioIncidencias.resolverIncidencia(idBici, motivo, true);
			} catch (RepositorioException | EntidadNoEncontrada e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		faltaMotivo = !correcto;
		volver(correcto);
	}
	
	public void darBajaIncidencia()
	{
		faltaNombre = false;
		boolean correcto = true;
		if(motivo == null||motivo.length() < 1)
			correcto=false;
		else
		{
			try {
				servicioIncidencias.resolverIncidencia(idBici, motivo, false);
			} catch (RepositorioException | EntidadNoEncontrada e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		volver(correcto);
	}
	
	private void volver(boolean correcto)
	{
		try {
			if(correcto)
				facesContext.getExternalContext().redirect("/bici/verIncidenciasAbiertas.xhtml");
			else
				facesContext.getExternalContext().redirect("/incidencia/gestionarIncidencia.xhtml?idBici="+idBici);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
