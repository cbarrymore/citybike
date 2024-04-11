package citybike.eventos.servicio;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IServicioEventos {

    public final String[] EVENTOS_EMITIR = { "bicicleta-alquilada", "bicicleta-alquiler-concluido" };
    // hashmap of eventid and associated method to process the event
    public final HashMap<String, Runnable> EVENTOS_RECIBIR = new HashMap<String, Runnable>();

    public void publicarEvento(String routingKey, EventosEmitir idEvento, String mensaje) throws IOException;

    public void suscribirse(String queue, String routingKey) throws IOException;

    public void procesarEvento(String idEvento, String contentType, String contenido)
            throws JsonParseException, JsonMappingException, IOException;
}