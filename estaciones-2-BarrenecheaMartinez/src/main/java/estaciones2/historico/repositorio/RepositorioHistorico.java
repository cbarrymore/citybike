package estaciones2.historico.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import estaciones2.bici.modelo.Bici;
import estaciones2.historico.modelo.Historico;

@NoRepositoryBean
public interface RepositorioHistorico extends CrudRepository<Historico, String> {
    public Historico findByIdBici(String idBici);
}
