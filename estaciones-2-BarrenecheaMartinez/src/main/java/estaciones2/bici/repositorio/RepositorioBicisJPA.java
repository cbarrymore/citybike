package estaciones2.bici.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estaciones2.bici.modelo.Bici;

@Repository
public interface RepositorioBicisJPA extends RepositorioBicis, JpaRepository<Bici, String>{

}
