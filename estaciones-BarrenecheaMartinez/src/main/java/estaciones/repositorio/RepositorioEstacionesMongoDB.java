package estaciones.repositorio;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.model.geojson.Point;


import estaciones.modelo.Estacion;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioEstacionesMongoDB extends RepositorioMongoDB<Estacion>
implements FiltroBusquedaEstaciones  {
	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Estacion> coleccion;
	protected MongoCollection<Document> coleccionSinCodificar;
	
	public RepositorioEstacionesMongoDB() {
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

			coleccion = database.getCollection("estaciones", Estacion.class).withCodecRegistry(defaultCodecRegistry);
		    coleccionSinCodificar = database.getCollection("estaciones");
		    coleccion.createIndex(Indexes.geo2dsphere("coordenadas"));
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public MongoCollection<Estacion> getColeccion() {
		return coleccion;
	}

	@Override
	public Set<Estacion> getEstacionesProximas(BigDecimal longitud, BigDecimal latitud) {
		
		Point coordenadas = new Point(
				 new Position(
						 List.of(longitud.doubleValue(),latitud.doubleValue())
				  )
		         );
		
		Bson filter = Filters.nearSphere("coordenadas", coordenadas, null, null);
        FindIterable<Estacion> resultados = coleccion.find(filter).limit(3);
        Set<Estacion> estacionesProximas = new HashSet<Estacion>();
        resultados.into(estacionesProximas);
        return estacionesProximas;

	}

	@Override
	public Estacion getEstacionLibre() {
		Document filtro = new Document("$expr", 
			    new Document("$lt", 
			        Arrays.asList(
			            new Document("$size", "$bicisAparcadas"),
			            "$numPuestos"
			        )
			    )
			);
		return getColeccion().find(filtro).first();
	}
	
}
