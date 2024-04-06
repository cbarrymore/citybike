package estaciones2.evento;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME;
    public static final EXCHANGE_NAME;
    public static final String ROUTING_KEY;
}