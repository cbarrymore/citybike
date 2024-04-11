package citybike.eventos.servicio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import citybike.alquiler.servicio.IServicioAlquileres;
import citybike.eventos.dtos.BicicletaDesactivadaEventDto;
import citybike.servicio.FactoriaServicios;

public class ServicioEventosRabbit implements IServicioEventos {

    ConnectionFactory factory;
    Connection connection;
    Channel channel;

    private static final String EXCHANGE_NAME = "Citybike";
    private static final String URI = "amqps://wjmgyyga:Ap642XV6hyZ0k0xwU4eTFmkH5DfyzHbt@whale.rmq.cloudamqp.com/wjmgyyga";
    private final HashMap<String, String> QUEUE_KEY = new HashMap<String, String>();

    // private IServicioAlquileres servicioAlquileres =
    // FactoriaServicios.getServicio(IServicioAlquileres.class);

    public ServicioEventosRabbit()
            throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
        this.factory = new ConnectionFactory();
        this.factory.setUri(URI);
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();

        this.QUEUE_KEY.put("citybike.alquiler", "citybike.alquiler");

        // subscribe to all queues in QUEUE_KEY

        for (String queue : QUEUE_KEY.keySet()) {
            channel.basicConsume(queue, false, queue + "-consumer",
                    new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope,
                                AMQP.BasicProperties properties, byte[] body) throws IOException {
                            String routingKey = envelope.getRoutingKey();
                            String contentType = properties.getContentType();
                            long deliveryTag = envelope.getDeliveryTag();
                            String contenido = new String(body);

                            procesarEvento(routingKey, contentType, contenido);

                            channel.basicAck(deliveryTag, false);
                        }
                    });

        }
    }

    @Override
    public void publicarEvento(String routingKey, EventosEmitir idEvento, String mensaje) throws IOException {
        String evento = EVENTOS_EMITIR[idEvento.ordinal()];
        String finalRoutingKey = routingKey + "." + evento;
        channel.basicPublish(EXCHANGE_NAME, finalRoutingKey,
                new AMQP.BasicProperties.Builder()
                        .contentType("application/json")
                        .build(),
                mensaje.getBytes());
    }

    @Override
    public void suscribirse(String queue, String routingKey) throws IOException {
        this.QUEUE_KEY.put(queue, routingKey);
        this.channel.basicConsume(queue, false, queue + "-consumer",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope,
                            AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        long deliveryTag = envelope.getDeliveryTag();
                        String contenido = new String(body);

                        procesarEvento(routingKey, contentType, contenido);

                        channel.basicAck(deliveryTag, false);
                    }
                });
    }

    @Override
    public void procesarEvento(String routingKey, String contentType, String contenido)
            throws JsonParseException, JsonMappingException, IOException {
        // extract eventId from routingKey (last part of routingKey by ".")
        String[] routingKeyParts = routingKey.split("\\.");
        String idEvento = routingKeyParts[routingKeyParts.length - 1];

        // if content type is application/json, parse the content to a dictionary
        if (contentType.equals("application/json")) {
            // process contenido as json

            Gson g = new Gson();
            switch (idEvento) {
                case "bicicleta-desactivada":
                    BicicletaDesactivadaEventDto evento = g.fromJson(contenido, BicicletaDesactivadaEventDto.class);
                    System.out.println("Evento recibido: " + idEvento);
                    break;
                case "bicicleta-activada":
                    break;
                default:
                    break;
            }

        }

        // for (String evento : EVENTOS_RECIBIR) {
        // if (evento.equals(idEvento)) {
        // System.out.println("Evento recibido: " + idEvento);
        // System.out.println("Contenido: " + contenido);
        // }
        // }

    }

}
