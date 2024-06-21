package citybike.usuario.modelo;

import java.util.ArrayList;
import java.util.List;

import citybike.alquiler.modelo.Alquiler;
import citybike.repositorio.Identificable;

public class Usuario implements Identificable {
	private String id;
	private ArrayList<Reserva> reservas;
	private ArrayList<Alquiler> alquileres;
	
	public Usuario()
	{
		this.alquileres = new ArrayList<Alquiler>();
		this.reservas = new ArrayList<Reserva>();
	}
	
	public Usuario(String id) {
		this.id = id;
		this.alquileres = new ArrayList<Alquiler>();
		this.reservas = new ArrayList<Reserva>();
	}
	
	public int reservasCaducadas() {
		return (int)reservas.stream().filter(r -> r.caducada()).count();
	}
	
	private int tiempoUsoHoy() {
		return alquileres.stream()
		.filter(a -> a.deHoy())
		.mapToInt(a -> a.tiempo())
		.reduce(0, Integer::sum);
	}
	
	private int tiempoUsoSemana() {
		return alquileres.stream()
				.filter(a -> a.deEstaSemana())
				.mapToInt(a -> a.tiempo())
				.reduce(0, Integer::sum);
	}
	
	public boolean superaTiempo() {
		return tiempoUsoHoy() >=60 || tiempoUsoSemana() >=180;
	}
	
	public Reserva reservaActiva() {
		if( reservas.size()== 0)
			return null;
		Reserva ultimaReserva = reservas.get(reservas.size()-1);
//		if(ultimaReserva.activa())
			return ultimaReserva;
//		return null;
	}
	
	public Alquiler alquilerActivo() {
		if(alquileres.size()==0)
			return null;
		Alquiler ultimoAlquiler= alquileres.get(alquileres.size()-1);
		if(ultimoAlquiler.activo())
			return ultimoAlquiler;
		return null;
	}
	
	public boolean bloqueado() {
		return reservas.stream()
				.filter(r -> r.caducada())
				.count() == 3;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = new ArrayList<Reserva>(reservas);
	}

	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	public void setAlquileres(List<Alquiler> alquileres) {
		this.alquileres = new ArrayList<Alquiler>(alquileres);
	}
	
	
}

