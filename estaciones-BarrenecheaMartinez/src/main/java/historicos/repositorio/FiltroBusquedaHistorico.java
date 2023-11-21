package historicos.repositorio;

import historicos.modelo.Historico;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public interface FiltroBusquedaHistorico extends Repositorio<Historico, String> {
	public Historico getByBiciId(String idBici) throws RepositorioException;
}
