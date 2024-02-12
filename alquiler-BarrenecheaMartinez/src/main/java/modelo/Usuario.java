package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Usuario {
	private String id;
	private List<Reserva> reservas;
	private List<Alquiler> alquileres;
	
	public int reservasCaducadas() {
		return (int)reservas.stream().filter(r -> r.caducada()).count();
	}
	
	private int tiempoUsoHoy() {
		return alquileres.stream()
		.filter(a -> a.activo())
		.mapToInt(a -> a.tiempo())
		.reduce(0, Integer::sum);
	}
	
	private int tiempoUsoSemana() {
		return alquileres.stream()
				.filter(a -> a.getInicio().isAfter(LocalDateTime.now().minusWeeks(1)))
				.mapToInt(a -> a.tiempo())
				.reduce(0, Integer::sum);
	}
	
	public boolean superaTiempo() {
		return tiempoUsoHoy() >=60 || tiempoUsoSemana() >=180;
	}
	
	public Reserva reservaActiva() {
		Reserva ultimaReserva = reservas.get(reservas.size());
		if(ultimaReserva.activa())
			return ultimaReserva;
		return null;
	}
	
	public Alquiler alquilerActivo() {
		Alquiler ultimoAlquiler= alquileres.get(alquileres.size());
		if(ultimoAlquiler.activo())
			return ultimoAlquiler;
		return null;
	}
	
	public boolean bloqueado() {
		return reservas.stream()
				.filter(r -> r.caducada())
				.count() == 3;
	}
	
	
}

