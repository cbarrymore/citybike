package citybike.eventos.servicio;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import citybike.eventos.dtos.Evento;
import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;

public interface IServicioEventos {

    public final String[] EVENTOS_EMITIR = { "bicicleta-alquilada", "bicicleta-alquiler-concluido" };
    // hashmap of eventid and associated method to process the event
    public final HashMap<String, Runnable> EVENTOS_RECIBIR = new HashMap<String, Runnable>();

    public void publicarEvento(Evento evento) throws IOException;

    public void suscribirse(String queue, String routingKey)
            throws IOException, RepositorioException, EntidadNoEncontrada;

    public void procesarEvento(String idEvento, String contentType, String contenido)
            throws JsonParseException, JsonMappingException, IOException, RepositorioException, EntidadNoEncontrada;
}