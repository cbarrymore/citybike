package estaciones2.estacion.servicio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import estaciones2.bici.modelo.Bici;
import estaciones2.estacion.modelo.Estacion;
import estaciones2.repositorio.EntidadNoEncontrada;
import estaciones2.repositorio.RepositorioException;

public interface IServicioEstaciones {

	public String altaEstacion(String nombre, int numeroPuestos, long dirPostal, BigDecimal longitud,
			BigDecimal latitud) throws RepositorioException;

	public Estacion obtenerEstacion(String id) throws RepositorioException, EntidadNoEncontrada;

	public String altaBici(String modelo, String idEstacion) throws RepositorioException, EntidadNoEncontrada;

	public void estacionarBici(String idEstacion, String idBici) throws RepositorioException, EntidadNoEncontrada;

	public void darBajaBici(String idBici, String motivo) throws RepositorioException, EntidadNoEncontrada;

	public List<Bici> bicisEstacion(String idEstaciones)
			throws RepositorioException, EntidadNoEncontrada;

	public Page<Bici> biciEstacionPaginado(String idEstacion, Pageable paginacion) throws RepositorioException;

	public List<Bici> bicisEstacionLimitado(String idEstaciones) throws RepositorioException, EntidadNoEncontrada;

	public Page<Bici> bicisEstacionLimitadoPaginado(String idEstaciones, Pageable paginacion)
			throws RepositorioException;

	public List<Estacion> obtenerEstaciones() throws RepositorioException;

	public Page<Estacion> obtenerEstacionesPaginado(Pageable paginacion);

	public boolean huecoDisponible(String idEstacion) throws RepositorioException, EntidadNoEncontrada;

    public void modificarEstacion(String id, String nombre, int numPuestos, long dirPostal, BigDecimal latitud,
            BigDecimal longitud);

    public void bajaEstacion(String id);

}
