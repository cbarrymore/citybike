package sitioTuristico.repositorio;

import repositorio.RepositorioJSON;
import sitioTuristico.modelo.SitioTuristico;

public class RepositorioJSONSitioTuristico extends RepositorioJSON<SitioTuristico> {
	public RepositorioJSONSitioTuristico() {
		
		try {
			SitioTuristico st = new SitioTuristico("Prueba","prueba",2.3,"http","prueba_1");

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
