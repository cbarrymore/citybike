package bicis.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import repositorio.Identificable;

@Entity
@Table(name="bici")
@NamedNativeQuery(name = "Bici.getBicisConIncidencias", query = "SELECT * FROM bici WHERE codigo  "
		+ "in (SELECT id_bici FROM incidencia WHERE estado in ('ASIGNADA', 'PENDIENTE') )"
		, resultClass = Bici.class)

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
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Incidencia> incidencias;
	
	
	public Bici()
	{
		
	}
	
	public Bici(String modelo, LocalDate fechaAlta)
	{
		this.modelo = modelo;
		this.fechaAlta = fechaAlta;
		this.incidencias = new LinkedList<Incidencia>();
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

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}
	
	public void addIncidencia(Incidencia incidencia)
	{
		incidencias.add(incidencia);
	}
	
	public boolean ultimaIncidenciaAbierta()
	{
		return!(incidencias.size() == 0 || !incidencias.get(0).isAbierta());
	}
	
	public Incidencia getUltimaIncidencia()
	{
		if(incidencias.size()==0)
			return null;
		return incidencias.get(0);
	}
}
