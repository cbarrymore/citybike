package persistencia;

import repositorio.Identificable;

public interface Entidad<T> extends Identificable
{
	public T getObject();
}
