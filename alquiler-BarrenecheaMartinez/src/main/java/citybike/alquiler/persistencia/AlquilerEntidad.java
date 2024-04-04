package citybike.alquiler.persistencia;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import citybike.alquiler.modelo.Alquiler;
import citybike.persistencia.Entidad;


@Entity
public class AlquilerEntidad implements Entidad<Alquiler>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(name = "id_bici")
	private String idBici;
	
	@Column(name = "fecha_inicio", columnDefinition = "TIMESTAMP")
	private LocalDateTime inicio;
	
	@Column(name = "fecha_fin", columnDefinition = "TIMESTAMP")
	private LocalDateTime fin;
	
	public AlquilerEntidad()
	{
		
	}
	
	public AlquilerEntidad(Alquiler alquiler)
	{
		this.idBici = alquiler.getIdBici();
		this.inicio = alquiler.getInicio();
		this.fin = alquiler.getFin();
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
		alq.setId(id);
		alq.setIdBici(idBici);
		alq.setInicio(inicio);
		alq.setFin(fin);
		return alq;
	}
	
}
