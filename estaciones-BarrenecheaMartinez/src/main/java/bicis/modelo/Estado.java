package bicis.modelo;

public enum Estado {
	CANCELADO,
	PENDIENTE,
	ASIGNADA,
	RESUELTA;
	
	public boolean abierta()
	{
		return this == Estado.CANCELADO || this == Estado.ASIGNADA || this == Estado.ASIGNADA;
	}
}
