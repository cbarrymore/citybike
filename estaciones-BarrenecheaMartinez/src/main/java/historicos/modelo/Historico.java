package historicos.modelo;

import java.time.LocalDate;
import java.util.List;

import repositorio.Identificable;

public class Historico implements Identificable{
	private String id;
	private String bici;
	private List<Registro> registros;
	public String getBici() {
		return bici;
	}
	public void setBici(String bici) {
		this.bici = bici;
	}
	public List<Registro> getRegistros() {
		return registros;
	}
	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
}
