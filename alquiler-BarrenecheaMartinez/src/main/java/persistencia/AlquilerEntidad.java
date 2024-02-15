package persistencia;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import modelo.Alquiler;

public class AlquilerEntidad {
	@Id
	private String id;
	
	@Column(name = "id_bici")
	private String idBici;
	
	@Column(name = "fecha_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime inicio;
	
	@Column(name = "fecha_fin")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fin;
	
	public AlquilerEntidad()
	{
		
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getIdBici()
	{
		return idBici;
	}

	public void setIdBici(String idBici)
	{
		this.idBici = idBici;
	}

	public LocalDateTime getInicio()
	{
		return inicio;
	}

	public void setInicio(LocalDateTime inicio)
	{
		this.inicio = inicio;
	}

	public LocalDateTime getFin()
	{
		return fin;
	}

	public void setFin(LocalDateTime fin)
	{
		this.fin = fin;
	}
	
	public Alquiler getObject()
	{
		Alquiler alq = new Alquiler();
		alq.setIdBici(idBici);
		alq.setInicio(inicio);
		alq.setFin(fin);
		return alq;
	}
	
}
