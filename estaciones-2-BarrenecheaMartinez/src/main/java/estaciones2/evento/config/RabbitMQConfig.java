package estaciones2.evento.config;

import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "citybike.estaciones2";
    public static final String QUEUE_NAME_ALQUILER = "citybike.alquiler";
    public static final String EXCHANGE_NAME = "Citybike";
    public static final String ROUTING_KEY = "citybike.estaciones2.";
    public static final String ROUTING_KEY_ALQUILER = "citybike.alquiler.";

    @Bean
    public TopicExchange exchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue()
    {
        boolean durable = true;
        boolean exclusive = false;
        boolean autodelete = false;
        return new Queue(QUEUE_NAME, durable, exclusive, autodelete);
    }
/*
    @Bean
    public Queue queueAlquiler()
    {
        boolean durable = true;
        boolean exclusive = false;
        boolean autodelete = false;
        return new Queue(QUEUE_NAME_ALQUILER, durable, exclusive, autodelete);
    }
*/
    @Bean
    public Binding binding(Queue queue, Exchange exchange)
    {
        Map<String, Object> propiedades = null;
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY+"*").and(propiedades);
    }
/* 
    @Bean
    public Binding bindingAlquiler(Queue queueAlquiler, Exchange exchange)
    {
        Map<String, Object> propiedades = null;
        return BindingBuilder.bind(queueAlquiler).to(exchange).with(ROUTING_KEY_ALQUILER+"*").and(propiedades);
    }
*/
    @Bean
    public MessageConverter jsonMessageConverter()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}