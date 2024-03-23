package repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import modelo.Estacion;

@Repository
public interface RepositorioEstacionesMongo
	extends RepositorioEstaciones, MongoRepository<Estacion, String> {
	
	
	
}
