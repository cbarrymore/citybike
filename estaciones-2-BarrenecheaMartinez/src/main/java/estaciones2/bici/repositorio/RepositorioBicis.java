package estaciones2.bici.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import estaciones2.bici.modelo.Bici;

@NoRepositoryBean
public interface RepositorioBicis extends PagingAndSortingRepository<Bici, String> {

    public Page<Bici> findByEstacion(String idEstacion, Pageable pageable);

    public Page<Bici> findByEstacionAndDisponible(String idEstacion, boolean disponible, Pageable pageable);
}