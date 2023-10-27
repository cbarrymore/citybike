package sitioTuristico.repositorio;

import java.util.LinkedList;

import repositorio.RepositorioJSON;
import sitioTuristico.modelo.InformacionCompleta;
import sitioTuristico.modelo.SitioTuristico;

public class RepositorioJSONSitioTuristico extends RepositorioJSON<SitioTuristico> {
	public RepositorioJSONSitioTuristico() {
		
		try {
			SitioTuristico st = new SitioTuristico("Prueba","prueba",2.3,"http","prueba_1");
			LinkedList<String> l = new LinkedList<String>();
			LinkedList<String> l2 = new LinkedList<String>();
			l.add("a");
			l2.add("c");
			st.setInfoCompleta(new InformacionCompleta(null,l,l2, null));
			this.add(st);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Class<SitioTuristico> getClase() {
		return SitioTuristico.class;
	}
}
