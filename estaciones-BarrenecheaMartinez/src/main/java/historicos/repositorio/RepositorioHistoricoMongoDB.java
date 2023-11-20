package historicos.repositorio;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import estaciones.modelo.Estacion;
import historicos.modelo.Historico;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioHistoricoMongoDB extends RepositorioMongoDB<Historico>{
	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Historico> coleccion;
	protected MongoCollection<Document> coleccionSinCodificar;
	
	public RepositorioHistoricoMongoDB() {
		PropertiesReader properties;
		try {
			properties = new PropertiesReader("mongo.properties");

			String connectionString = properties.getProperty("mongouri");

			MongoClient mongoClient = MongoClients.create(connectionString);

			String mongoDatabase = properties.getProperty("mongodatabase");

			database = mongoClient.getDatabase(mongoDatabase);

			CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			coleccion = database.getCollection("historicos", Historico.class).withCodecRegistry(defaultCodecRegistry);
		    coleccionSinCodificar = database.getCollection("historicos");
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public MongoCollection<Historico> getColeccion() {
		return coleccion;
	}

}
