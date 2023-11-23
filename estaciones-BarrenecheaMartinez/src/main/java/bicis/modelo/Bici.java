package bicis.modelo;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mysql.cj.x.protobuf.MysqlxCursor.Fetch;

import repositorio.Identificable;

@Entity
@Table(name="bici")
@NamedQuery(name = "Bici.getBicisConIncidencias", query = "SELECT b FROM Bici b WHERE b.incidencia IS NOT NULL")
public class Bici implements Identificable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private String codigo;
	@Column(name="fecha_de_alta", columnDefinition = "DATE")
	private LocalDate fechaAlta;
	@Column(name="modelo")
	private String modelo;
	
	@Column(name="fecha_de_baja", columnDefinition = "DATE")
	private LocalDate fechaBaja;
	@Column(name = "motivo_de_baja")
	private String motivoBaja;
	
	@Column (name = "disponible")
	private boolean disponible;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Incidencia incidencia;
	
	
	public Bici()
	{
		
	}
	
	public Bici(String modelo, LocalDate fechaAlta)
	{
		this.modelo = modelo;
		this.fechaAlta = fechaAlta;
	}
	
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

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}
}
