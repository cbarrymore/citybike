








package repositorio;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public abstract class RepositorioXML<T extends Identificable> implements RepositorioString<T> {

	@Override
	public String add(T entity) throws RepositorioException {
		String id = UUID.randomUUID().toString();
		entity.setId(id);

		// store entity
		try {

			JAXBContext contexto = JAXBContext.newInstance(entity.getClass());
			// Empaquetado en un documento XML (marshalling)

			Marshaller marshaller = contexto.createMarshaller();

			marshaller.setProperty("jaxb.formatted.output", true);

			marshaller.marshal(entity, new File("xml/" + id + ".xml"));
		} catch (Exception e) {
			throw new RepositorioException("Fallo al guardar " + entity, e);
		}
		return id;
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		String id = entity.getId();

		// query document exists

		File file = new File("xml/" + id + ".xml");
		if (!file.exists()) {
			throw new EntidadNoEncontrada("No existe: " + id);
		}

		// store updated entity
		try {

			JAXBContext contexto = JAXBContext.newInstance(entity.getClass());
			// Empaquetado en un documento XML (marshalling)

			Marshaller marshaller = contexto.createMarshaller();

			marshaller.setProperty("jaxb.formatted.output", true);

			marshaller.marshal(entity, new File("xml/" + id + ".xml"));
		} catch (Exception e) {
			throw new RepositorioException("Fallo al guardar " + entity, e);
		}

	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		String id = entity.getId();
		// query document exists
		File file = new File("xml/" + id + ".xml");
		if (!file.exists()) {
			throw new EntidadNoEncontrada("No existe: " + id);
		}
		// delete from document
		file.delete(); // TODO comprobar si se ha borrado
	}

	public abstract Class<T> getClase();

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub

		File file = new File("xml/" + id + ".xml");
		if (!file.exists()) {
			throw new EntidadNoEncontrada("No existe: " + id);
		}
		// find entity in document

		try {
			JAXBContext contexto = JAXBContext.newInstance(getClase());
			Unmarshaller unmarshaller = contexto.createUnmarshaller();
	
			T entity = (T) unmarshaller.unmarshal(new File("xml/" + id + ".xml"));
			return entity;
		}
		catch(Exception e) {
			throw new RepositorioException("Fallo al recuperar" + id,e);
		}
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		LinkedList<String> ids = new LinkedList<String>();
		File dir = new File("xml/");
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
