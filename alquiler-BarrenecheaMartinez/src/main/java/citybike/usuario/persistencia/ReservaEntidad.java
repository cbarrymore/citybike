package citybike.usuario.persistencia;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import citybike.persistencia.Entidad;
import citybike.usuario.modelo.Reserva;

@Entity
public class ReservaEntidad implements Entidad<Reserva>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(name = "id_bici")
	private String idBici;
	
	@Column(name = "fecha_creada",nullable = true)
	private String creada;
	
	@Column(name = "fecha_caducidad",nullable = true)
	private String caducidad;
	
	public ReservaEntidad()
	{
		
	}

	public ReservaEntidad(Reserva reserva)
	{
		this.idBici = reserva.getIdBici();
		this.creada = reserva.getCreada().toString();
		this.caducidad = reserva.getCaducidad().toString();
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

	public String getCreada()
	{
		return creada;
	}

	public void setCreada(LocalDateTime creada)
	{
		this.creada = creada.toString();
	}

	public String getCaducidad()
	{
		return caducidad;
	}

	public void setCaducidad(LocalDateTime caducidad)
	{
		this.caducidad = caducidad.toString();
	}
	
	public Reserva getObject()
	{
		Reserva res = new Reserva();
		res.setId(id);
		res.setIdBici(idBici);
		res.setCreada(LocalDateTime.parse(creada));
		res.setCaducidad(LocalDateTime.parse(caducidad));
		return res;
	}
	
}
