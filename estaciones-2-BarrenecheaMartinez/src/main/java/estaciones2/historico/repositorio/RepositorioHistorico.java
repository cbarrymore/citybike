package estaciones2.historico.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import estaciones2.historico.modelo.Historico;

@NoRepositoryBean
public interface RepositorioHistorico extends CrudRepository<Historico, String>
{

}
