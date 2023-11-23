package bicis.repositorio;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mongodb.client.MongoCollection;

import bicis.modelo.Bici;
import historicos.modelo.Historico;
import repositorio.RepositorioJPA;
import repositorio.RepositorioMongoDB;
import utils.EntityManagerHelper;

public class RepositorioBiciJPA extends RepositorioJPA<Bici> implements FiltroBusquedaBici{

	@Override
	public Class<Bici> getClase() {
		return Bici.class;
	}

	@Override
	public String getNombre() {
		return Bici.class.getName().substring(Bici.class.getName().lastIndexOf(".") + 1);
	}
	
	@Override
	public List<Bici> getBicisConIncidencias() {
		EntityManager em = EntityManagerHelper.getEntityManager();
		Query query = em.createNamedQuery("Bici.getBicisConIncidencias",Bici.class);
		return query.getResultList();
	}
}
