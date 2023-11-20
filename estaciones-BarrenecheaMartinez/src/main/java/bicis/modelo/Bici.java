package bicis.modelo;

import java.time.LocalDate;

import repositorio.Identificable;

public class Bici implements Identificable{
	private String codigo;
	private LocalDate fechaAlta;
	private String modelo;
	private LocalDate fechaBaja;
	private String motivoBaja;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public LocalDate getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getMotivoBaja() {
		return motivoBaja;
	}
	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}
	@Override
	public String getId() {
		return codigo;
	}
	@Override
	public void setId(String codigo) {
		this.codigo = codigo;
		
	}
}
