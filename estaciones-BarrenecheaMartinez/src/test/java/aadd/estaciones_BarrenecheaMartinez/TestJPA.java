package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import bicis.modelo.Bici;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

class TestJPA {

	@Test
	void test() throws RepositorioException {
		RepositorioString<Bici> repo = FactoriaRepositorios.getRepositorio(Bici.class);
		Bici bici = new Bici("BMX", LocalDate.now());
		bici.setCodigo(UUID.randomUUID().toString());
		System.out.println(bici.getCodigo());
		repo.add(bici);
		try {
			repo.delete(bici);
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
