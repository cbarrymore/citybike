package sitioTuristico.modelo;

import java.util.ArrayList;
import java.util.List;

public class InformacionCompleta {

	private String resumenWikipedia;
	private List<String> categorias;
	private List<String> infoComplementaria;
	private String imagen;
	
	
	public InformacionCompleta(String resumenWikipedia, List<String> categorias, List<String> infoComplementaria,String imagen) {
		this.categorias = new ArrayList<String>(categorias);
		this.imagen = imagen;
		this.infoComplementaria = new ArrayList<String>(infoComplementaria);
		this.resumenWikipedia = resumenWikipedia;
	}

	public String getResumenWikipedia() {
		return resumenWikipedia;
	}

	public void setResumenWikipedia(String resumenWikipedia) {
		this.resumenWikipedia = resumenWikipedia;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public List<String> getInfoComplementaria() {
		return infoComplementaria;
	}

	public void setInfoComplementaria(List<String> infoComplementaria) {
		this.infoComplementaria = infoComplementaria;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
}
