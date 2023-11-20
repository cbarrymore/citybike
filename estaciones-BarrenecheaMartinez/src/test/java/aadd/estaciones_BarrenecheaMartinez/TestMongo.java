package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import historicos.modelo.Historico;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import repositorio.RepositorioString;

class TestMongo {
	
	
	@Test
	public void consulta() throws RepositorioException {
		RepositorioString<Historico> r = FactoriaRepositorios.getRepositorio(Historico.class);
		r.getAll();
	}

}
