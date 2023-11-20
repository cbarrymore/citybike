package bicis.repositorio;

import com.mongodb.client.MongoCollection;

import bicis.modelo.Bici;
import historicos.modelo.Historico;
import repositorio.RepositorioJPA;
import repositorio.RepositorioMongoDB;

public class RepositorioBiciJPA extends RepositorioJPA<Bici> {

	@Override
	public Class<Bici> getClase() {
		return Bici.class;
	}

	@Override
	public String getNombre() {
		return Bici.class.getName().substring(Bici.class.getName().lastIndexOf(".") + 1);
	}
}
