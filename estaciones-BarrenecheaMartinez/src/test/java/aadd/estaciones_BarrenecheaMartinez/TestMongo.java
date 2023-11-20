package aadd.estaciones_BarrenecheaMartinez;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import historicos.modelo.Historico;
import historicos.modelo.Registro;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import repositorio.RepositorioString;
import utils.PropertiesReader;

class TestMongo {
	
	private RepositorioString<Historico> repo;

	@BeforeEach
	public void setUp() throws RepositorioException {
		repo = FactoriaRepositorios.getRepositorio(Historico.class);
	}
	@Test
	public void consulta() throws RepositorioException {
		PropertiesReader properties;
		try {
			properties = new PropertiesReader("mongo.properties");

			String connectionString = properties.getProperty("mongouri");

			MongoClient mongoClient = MongoClients.create(connectionString);

			String mongoDatabase = properties.getProperty("mongodatabase");

			MongoDatabase database = mongoClient.getDatabase(mongoDatabase);

			CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			MongoCollection<Historico> coleccion = database.getCollection("historicos", Historico.class).withCodecRegistry(defaultCodecRegistry);
			MongoCollection<Document> coleccionSinCodificar = database.getCollection("historicos");
		    FindIterable<Historico> a = coleccion.find();
		    List<Historico> resultList = new ArrayList<>();
	        a.into(resultList);
	        System.out.println(resultList.get(0).toString());
		} catch (Exception e) {

		}
		
	}
	@Test
	public void consulta2() throws RepositorioException {
		RepositorioString<Historico> repo = FactoriaRepositorios.getRepositorio(Historico.class);
		List<Historico> a = repo.getAll();
		a.forEach(System.out::print);
	}
	@Test
	public void insertar() {
		List<Registro> l = new ArrayList<Registro>();
		l.add(new Registro());
		repo.add(new Historico("hola", )))
	}

}
