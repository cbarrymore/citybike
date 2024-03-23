package servicio;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositorio.RepositorioEstaciones;

@Service
@Transactional
public class ServicioEstaciones {

	private RepositorioEstaciones repoEstaciones;
	
	@Autowired
	public ServicioEstaciones(RepositorioEstaciones repoEstaciones)
	{
		this.repoEstaciones = repoEstaciones;
	}
	
}
