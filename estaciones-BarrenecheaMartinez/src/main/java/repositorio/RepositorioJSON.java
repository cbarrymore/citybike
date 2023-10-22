package repositorio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.spi.JsonbProvider;

public abstract class RepositorioJSON<T extends Identificable> implements RepositorioString<T>
{

	@Override
	public String add(T entity) throws RepositorioException {
		// TODO Auto-generated method stub
		Jsonb contexto = JsonbProvider.provider().create().build();
		String entityJson = contexto.toJson(entity);
		try {
			FileWriter fichero = new FileWriter("json/"+entity.getId()+".json");
			fichero.write(entityJson);
			fichero.close();
			return entity.getId();
		} catch (IOException e) {
			throw new RepositorioException("Fallo al guardar "+entity.getId(),e);
		}
		
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada
	{
		String rutaFichero = "json/"+entity.getId()+".json";
		File fichero = new File(rutaFichero);
		if(!fichero.exists())
			throw new EntidadNoEncontrada("No se encuentra la entidad con id "+entity.getId());
		Jsonb contexto = JsonbProvider.provider().create().build();
		String entityJson = contexto.toJson(entity);
		try {
			FileWriter nuevofichero = new FileWriter("json/"+entity.getId()+".json");
			nuevofichero.write(entityJson);
			nuevofichero.close();
		} catch (IOException e) {
			throw new RepositorioException("Fallo al guardar "+entity.getId(),e);
		}
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		String id = entity.getId();
		// query document exists
		File fichero = new File("json/" + id + ".json");
		if (!fichero.exists()) {
			throw new EntidadNoEncontrada("No existe: " + id);
		}
		// delete from document
		fichero.delete();
		if(fichero.exists())
			throw new RepositorioException("Fallo al eliminar "+entity.getId());
		
	}
	
	public abstract Class<T> getClase();

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		String rutaFichero = "json/"+id+".json";
		File fichero = new File(rutaFichero);
		try {
			Reader entrada = new FileReader(fichero);
			Jsonb contexto = JsonbProvider.provider().create().build();
			T entity = contexto.fromJson(entrada, getClase());
			return entity;
		} catch (FileNotFoundException e) {
			throw new EntidadNoEncontrada("No se encuentra la entidad con id "+id);
		}
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		LinkedList<T> result = new LinkedList<>();

		for (String id : getIds()) {
			try {
				T entity = getById(id);
				result.add(entity);
			} catch (EntidadNoEncontrada e) {
				// No va a suceder
			}
		}
		// return al entities

		return result;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		LinkedList<String> ids = new LinkedList<String>();
		File dir = new File("json/");
		if(dir.isDirectory()) {
			File files[] = dir.listFiles();
			for (File file : files) {
				ids.add(file.getName());
			}
		}
		// get all stored IDs

		return ids;
	}
	
}
