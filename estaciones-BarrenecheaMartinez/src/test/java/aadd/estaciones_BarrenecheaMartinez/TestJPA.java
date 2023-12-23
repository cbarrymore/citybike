package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import bicis.modelo.Bici;
import bicis.modelo.Incidencia;
import bicis.repositorio.RepositorioBiciJPA;
import historicos.repositorio.RepositorioHistoricoMongoDB;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

class TestJPA {

	@Test
	void test() throws RepositorioException {
		RepositorioString<Bici> repo = FactoriaRepositorios.getRepositorio(Bici.class);
		Bici bici = new Bici("BMX", LocalDate.now());
		String id=repo.add(bici);
		System.out.println(id + " " + bici.getId());
		bici.addIncidencia(new Incidencia("hola",id));
		try {
			repo.update(bici);
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
//		try {
//			repo.delete(bici);
//		} catch (RepositorioException | EntidadNoEncontrada e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		bici.addIncidencia(new Incidencia("hola",id));
	}
	@Test
	void test2() {
		RepositorioBiciJPA repo = FactoriaRepositorios.getRepositorio(Bici.class);
		
		System.out.println(repo.getBicisConIncidencias().toString());
	}
	
//	@Test
//	public void borrarBici() {
//		RepositorioString<Bici> repo = FactoriaRepositorios.getRepositorio(Bici.class);
//		try {
//			Bici b= repo.getById("351");
//			repo.delete(b);
//		} catch (RepositorioException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (EntidadNoEncontrada e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//	}
}
