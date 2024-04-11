package estaciones2.evento.bus;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import estaciones2.evento.config.RabbitMQConfig;
import estaciones2.evento.modelo.EventoBiciAlquilada;
import estaciones2.evento.modelo.EventoBiciAlquilerTerminado;
import estaciones2.evento.servicio.ServicioEventos;
import estaciones2.repositorio.EntidadNoEncontrada;

@Component
public class ListenerEventos {

    
    @Autowired
    private ServicioEventos servicioEventos;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleEvent(Message evento) throws EntidadNoEncontrada, JsonMappingException, JsonProcessingException
    {
        JsonNode preObjeto;
        String msgEvento = new String(evento.getBody());
        System.out.println(msgEvento);
        ObjectMapper objectMapper = new ObjectMapper();
        preObjeto = objectMapper.readTree(msgEvento);
        String idEvento = preObjeto.get("idEvento").asText();
        if(idEvento.equals(EventoBiciAlquilada.ID_EVENTO))
        {
            EventoBiciAlquilada ev = objectMapper.readValue(msgEvento, EventoBiciAlquilada.class);
            servicioEventos.biciAlquilada(ev.getIdBici(), ev.getActualFechaAlquiler());          
        }
        else if(idEvento.equals(EventoBiciAlquilerTerminado.ID_EVENTO))
        {
            EventoBiciAlquilerTerminado ev = objectMapper.readValue(msgEvento, EventoBiciAlquilerTerminado.class);
            servicioEventos.biciTerminaAlquiler(ev.getIdBici(), ev.getActualFechaFinAlquiler());
        }    
        
    }
}
