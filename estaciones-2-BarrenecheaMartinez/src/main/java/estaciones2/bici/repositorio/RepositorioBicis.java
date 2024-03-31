package estaciones2.bici.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import estaciones2.bici.modelo.Bici;

@NoRepositoryBean
public interface RepositorioBicis extends CrudRepository<Bici, String> {

}