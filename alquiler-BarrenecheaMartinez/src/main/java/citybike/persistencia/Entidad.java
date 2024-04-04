package citybike.persistencia;

import citybike.repositorio.Identificable;

public interface Entidad<T> extends Identificable
{
	public T getObject();
}
