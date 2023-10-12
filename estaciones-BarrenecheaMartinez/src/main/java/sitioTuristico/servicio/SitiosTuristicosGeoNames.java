package sitioTuristico.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import sitioTuristico.modelo.InformacionCompleta;
import sitioTuristico.modelo.SitioTuristico;

public class SitiosTuristicosGeoNames implements SitiosTuristicos {
	
	private static final String FIND_NEARBY_WIKIPEDIA = "http://api.geonames.org/findNearbyWikipedia?username=aadd&lang=es";
	private static final String DBPEDIA = "https://es.dbpedia.org/data/";
	private static final String DBPEDIA_NOMBRE = " http://www.w3.org/2000/01/rdf-schema#label";
	private static final String DBPEDIA_RESUMEN = "http://dbpedia.org/ontology/abstract";
	private static final String DBPEDIA_CATEGORIAS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	private static final String DBPEDIA_ENLACES_COMPLEMENTARIOS = "http://dbpedia.org/ontology/wikiPageExternalLink\r\n";
	private static final String DBPEDIA_IMAGEN = "http://es.dbpedia.org/property/imagen";
	//private Repositorio<SitioTuristico, String> repositorio = FactoriaRepositorios.getRepositorio(SitioTuristico.class);
	
	@Override
	public Collection<SitioTuristico> obtenerSitiosInteres(BigDecimal latitud, BigDecimal longitud) {
		Collection<SitioTuristico> coleccion = new ArrayList<SitioTuristico>();
		Document dom;
		DocumentBuilder analizador;
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		try {
			analizador = factoria.newDocumentBuilder();
			String formatted_str = FIND_NEARBY_WIKIPEDIA + "&lat=" +latitud.toString() + "&lng=" + longitud.toString();
			System.out.println(formatted_str);
			dom= analizador.parse(formatted_str);
			NodeList nodeListEntries= dom.getElementsByTagName("entry");
			for (int i =0; i< nodeListEntries.getLength();i++) {
				Element entry = (Element) nodeListEntries.item(i);
		        
				String descripcion = entry.getElementsByTagName("summary").item(0).getTextContent();
		        Double distancia = Double.parseDouble(entry.getElementsByTagName("distance").item(0).getTextContent());
		        String url = entry.getElementsByTagName("wikipediaUrl").item(0).getTextContent();
		        String nombre = entry.getElementsByTagName("title").item(0).getTextContent();
		        
		        String[] splitted_url= url.split("/");
		        String id = splitted_url[splitted_url.length-1];
		        
				SitioTuristico sitio = new SitioTuristico(nombre,descripcion,distancia,url,id);
				coleccion.add(sitio);
			}
			return coleccion;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public InformacionCompleta obtenerInformacionSiitoInteres(String idSitio) {
		String url=DBPEDIA +idSitio + ".json";
		try {
			InputStream source = new URL(url).openStream();
			JsonReader jsonReader = Json.createReader(source);
			JsonObject obj = jsonReader.readObject();
			System.out.println(obj.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		return null;
	}
	
}
