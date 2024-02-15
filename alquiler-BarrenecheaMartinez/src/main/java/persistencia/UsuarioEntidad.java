package persistencia;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import modelo.Reserva;
import modelo.Usuario;
import repositorio.Identificable;


@Entity
public class UsuarioEntidad implements Identificable {

	@Id
	private String id;
	@OneToMany
	private List<ReservaEntidad> reservas;
	@OneToMany
	private List<AlquilerEntidad> alquileres;
	
	public UsuarioEntidad()
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

	public List<ReservaEntidad> getReservas()
	{
		return reservas;
	}

	public void setReservas(List<ReservaEntidad> reservas)
	{
		this.reservas = reservas;
	}

	public List<AlquilerEntidad> getAlquileres()
	{
		return alquileres;
	}

	public void setAlquileres(List<AlquilerEntidad> alquileres)
	{
		this.alquileres = alquileres;
	}
	
	public Usuario getObject()
	{
		Usuario us = new Usuario();
		return us;
	}
	
}
