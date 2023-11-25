package repositorio;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public abstract class RepositorioMongoDB<T extends Identificable> implements RepositorioString<T> {
	
	public abstract MongoCollection<T> getColeccion();

	@Override
	public String add(T entity) throws RepositorioException {
		InsertOneResult result = getColeccion().insertOne(entity);
		if (result.getInsertedId() == null) {
			throw new RepositorioException("Error insertando al documento");
		}
		return result.getInsertedId().asObjectId().getValue().toString();
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		UpdateResult result = getColeccion().replaceOne(new Document("_id", new ObjectId(entity.getId())), entity);
		if (result.getMatchedCount() == 0) {
			throw new EntidadNoEncontrada("El documento a actualizar no existe");
		}
		if (!result.wasAcknowledged()) {
			throw new RepositorioException("El documento no se ha podido actualizar");
		}

	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		DeleteResult result = getColeccion().deleteOne(new Document("_id", new ObjectId(entity.getId())));
		if (result.getDeletedCount() == 0) {
			throw new EntidadNoEncontrada("El documento a actualizar no existe");
		}
		if (!result.wasAcknowledged()) {
			throw new RepositorioException("El documento no se ha podido actualizar");
		}

	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
	    try {
	        T result = getColeccion().find(new Document("_id", new ObjectId(id))).first();
	        if (result == null) {
	            throw new EntidadNoEncontrada("No se encontró la entidad");
	        }
	        return result;
	    } catch (Exception e) {
	        throw new RepositorioException("Error al obtener la entidad por ID", e);
	    }
	}


	@Override
	public List<T> getAll() throws RepositorioException {
	    try {
	        MongoIterable<T> iterable = getColeccion().find();
	        List<T> resultList = new ArrayList<>();

	        iterable.into(resultList);

	        return resultList;
	    } catch (Exception e) {
	        throw new RepositorioException("Error al obtener todos los documentos", e);
	    }
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		List<String> result = new ArrayList<String>();
		getColeccion().find().iterator().forEachRemaining(t -> result.add(t.getId()));
		return result;
	}


}
