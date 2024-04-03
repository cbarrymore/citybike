package estaciones2.bici.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import estaciones2.bici.modelo.Bici;

@Repository
public interface RepositorioBicisJPA extends RepositorioBicis, MongoRepository<Bici, String>{

}
