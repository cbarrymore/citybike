package estaciones2.estacion.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones2.estacion.modelo.Estacion;

@NoRepositoryBean
public interface RepositorioEstaciones extends PagingAndSortingRepository<Estacion, String> {

}
