package citybike.usuario.persistencia;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import citybike.alquiler.persistencia.AlquilerEntidad;
import citybike.persistencia.Entidad;
import citybike.usuario.modelo.Usuario;


@Entity
public class UsuarioEntidad implements Entidad<Usuario> {

	@Id
	private String id;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ReservaEntidad> reservas;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<AlquilerEntidad> alquileres;
	
	public UsuarioEntidad()
	{
		
	}

	public UsuarioEntidad(Usuario usuario)
	{
		this.id = usuario.getId();
		this.reservas = usuario.getReservas().stream().map(r -> new ReservaEntidad(r)).collect(Collectors.toList());
		this.alquileres = usuario.getAlquileres().stream().map(a -> new AlquilerEntidad(a)).collect(Collectors.toList());
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
		us.setId(id);
		us.setAlquileres(alquileres.stream().map(a -> a.getObject()).collect(Collectors.toList()));
		us.setReservas(reservas.stream().map(r -> r.getObject()).collect(Collectors.toList()));
		return us;
	}
	
}
