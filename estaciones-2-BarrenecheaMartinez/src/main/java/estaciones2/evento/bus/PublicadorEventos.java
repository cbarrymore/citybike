package estaciones2.evento.bus;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import estaciones2.evento.config.RabbitMQConfig;
import estaciones2.evento.modelo.Evento;

@Component
public class PublicadorEventos {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendMessage(Evento evento)
    {
        try
        {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.ROUTING_KEY+evento.getIdEvento(),evento);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
}
