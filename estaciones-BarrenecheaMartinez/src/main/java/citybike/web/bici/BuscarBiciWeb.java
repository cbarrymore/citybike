package citybike.web.bici;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import bicis.dto.BiciDTO;
import estaciones.modelo.Estacion;
import estaciones.servicio.IServicioEstaciones;
import estaciones.servicio.ServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named 
@ViewScoped
public class BuscarBiciWeb implements Serializable{
	
	private double latitud;
	private double longitud;
	
	private ArrayList<BiciDTO> bicis;
	private IServicioEstaciones servicioEstaciones;
	@Inject
    protected FacesContext facesContext;

	public BuscarBiciWeb() {
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	}
	
	
	public void buscar() {
		
		try {
	        //if (bicis == null ) {
	        	bicis = (ArrayList<BiciDTO>) servicioEstaciones.bicisDTOCercanas(BigDecimal.valueOf(longitud), BigDecimal.valueOf(latitud));
//	        	bicis = new ArrayList<BiciDTO>(List.of(new BiciDTO("3", LocalDate.now(), "a", LocalDate.now(), "b", false, null)));
	        //}
//			try {
//				facesContext.getExternalContext().redirect("buscarBicis.xhtml");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public double getLatitud() {
		return latitud;
	}


	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}


	public double getLongitud() {
		return longitud;
	}


	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}


	public ArrayList<BiciDTO> getBicis() {
		return bicis;
	}


	public void setBicis(ArrayList<BiciDTO> bicis) {
		this.bicis = bicis;
	}
}