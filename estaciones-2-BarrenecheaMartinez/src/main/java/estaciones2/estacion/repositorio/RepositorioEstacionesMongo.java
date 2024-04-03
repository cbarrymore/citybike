package estaciones2.estacion.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import estaciones2.estacion.modelo.Estacion;

public interface RepositorioEstacionesMongo
	extends RepositorioEstaciones, MongoRepository<Estacion, String> {
	
	
	
}
