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
	
	@Column(name = "fecha_inicio",nullable = true)
	private String inicio;
	
	@Column(name = "fecha_fin",nullable = true)
	private String fin;
	
	public AlquilerEntidad()
	{
		
	}
	
	public AlquilerEntidad(Alquiler alquiler)
	{
		this.idBici = alquiler.getIdBici();
		this.inicio = alquiler.getInicio().toString();
		this.fin = alquiler.getFin().toString();
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

	public String getInicio()
	{
		return inicio;
	}

	public void setInicio(LocalDateTime inicio)
	{
		this.inicio = inicio.toString();
	}

	public String getFin()
	{
		return fin;
	}

	public void setFin(LocalDateTime fin)
	{
		this.fin = fin.toString();
	}
	
	public Alquiler getObject()
	{
		Alquiler alq = new Alquiler();
		alq.setId(id);
		alq.setIdBici(idBici);
		alq.setInicio(LocalDateTime.parse(inicio));
		alq.setFin(LocalDateTime.parse(fin));
		return alq;
	}
	
}
