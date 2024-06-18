package estaciones2.bici.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import estaciones2.bici.modelo.Bici;

public interface RepositorioBicisMongo extends RepositorioBicis, MongoRepository<Bici, String> {

}
