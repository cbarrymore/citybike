package estaciones2.bici.modelo;

public enum Estado {
	CANCELADO,
	PENDIENTE,
	ASIGNADA,
	RESUELTA;
	
	public boolean abierta()
	{
		return this == Estado.ASIGNADA || this == Estado.PENDIENTE;
	}
}
