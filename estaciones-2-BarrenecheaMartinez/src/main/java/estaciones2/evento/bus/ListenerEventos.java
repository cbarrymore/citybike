package estaciones2.evento.bus;

import org.aspectj.bridge.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import estaciones2.evento.config.RabbitMQConfig;

@Component
public class ListenerEventos {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME) //Esto seguramente se cambia
    public void handleEvent(Message evento)
    {

    }
}
