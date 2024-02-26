package repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import persistencia.Entidad;
import utils.EntityManagerHelper;

public abstract class RepositorioJPA<T extends Identificable, K extends Entidad<T>> implements RepositorioString<T> {
	
	public abstract Class<K> getClase();
	public abstract String getNombre();
	@Override
	public String add(T object) throws RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		Entidad<T> entity = getEntidad(object);
		try {
			em.getTransaction().begin();
			em.persist(entity);
			object.setId(entity.getId());
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al guardar la entidad con id "+entity.getId(),e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
		return entity.getId();
	}

	@Override
	public void update(T object) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		Entidad<T> entity = getEntidad(object);
		try {
			em.getTransaction().begin();
			
			Entidad<T> instance = em.find(getClase(), entity.getId());
			if(instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			entity = em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al actualizar la entidad con id "+entity.getId(),e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public void delete(T object) throws RepositorioException, EntidadNoEncontrada {
		EntityManager em = EntityManagerHelper.getEntityManager();
		Entidad<T> entity = getEntidad(object);
		try {
			em.getTransaction().begin();
			Entidad<T> instance = em.find(getClase(), entity.getId());
			if(instance == null) {
				throw new EntidadNoEncontrada(entity.getId() + " no existe en el repositorio");
			}
			em.remove(instance);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new RepositorioException("Error al borrar la entidad con id "+entity.getId(),e);
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			EntityManagerHelper.closeEntityManager();
		}
	}

	@Override
	public T getById(String id) throws EntidadNoEncontrada, RepositorioException {
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {			
			Entidad<T> instance = em.find(getClase(), id);

			if (instance != null) {
				em.refresh(instance);
			} else {
				throw new EntidadNoEncontrada(id + " no existe en el repositorio");
			}

			return instance.getObject();

		} catch (RuntimeException re) {
			throw new RepositorioException("Error al recuperar la entidad con id "+id,re);
		}
	}

	@Override
	public List<T> getAll() throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model from " + getNombre() + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			List<Entidad<T>> lista = query.getResultList();
			return lista.stream().map(e -> e.getObject()).toList();

		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todas las entidades de "+getNombre(),re);

		}
	}

	@Override
	public List<String> getIds() throws RepositorioException{
		EntityManager em = EntityManagerHelper.getEntityManager();
		try {
			final String queryString = " SELECT model.id from " + getNombre() + " model ";

			Query query = em.createQuery(queryString);

			query.setHint(QueryHints.REFRESH, HintValues.TRUE);

			return query.getResultList();

		} catch (RuntimeException re) {

			throw new RepositorioException("Error buscando todos los ids de "+getNombre(),re);

		}
	}

	protected abstract Entidad<T> getEntidad(T object);
}

