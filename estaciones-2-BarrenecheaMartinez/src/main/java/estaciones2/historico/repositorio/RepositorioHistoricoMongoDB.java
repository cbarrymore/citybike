package estaciones2.historico.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import estaciones2.historico.modelo.Historico;

@Repository
public interface RepositorioHistoricoMongoDB extends RepositorioHistorico, MongoRepository<Historico, String> {

    public Historico findByIdBici(String idBici);
}
