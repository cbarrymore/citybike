package citybike.eventos.servicio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import citybike.alquiler.servicio.IServicioAlquileres;
import citybike.eventos.dtos.Evento;
import citybike.eventos.dtos.EventoBiciDesactivada;
import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;
import citybike.servicio.FactoriaServicios;

public class ServicioEventosRabbit implements IServicioEventos {

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;

    private static final String EXCHANGE_NAME = "Citybike";
    private static final String URI = "amqp://guest:guest@localhost:5672";
    private static final String ROUTING_KEY = "citybike.alquiler";
    private static final String ROUTING_KEY_ESCUCHAR = "citybike.estaciones2";
    private final HashMap<String, String> QUEUE_KEY = new HashMap<String, String>();

    private IServicioAlquileres servicioAlquileres = FactoriaServicios.getServicio(IServicioAlquileres.class);

    public ServicioEventosRabbit()
            throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
        this.factory = new ConnectionFactory();
        this.factory.setUri(URI);
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true, false, null);
        channel.queueDeclare("citibyke.alquiler", true, false, false, null);
        channel.queueBind("citibyke.alquiler", EXCHANGE_NAME, ROUTING_KEY_ESCUCHAR);
        this.QUEUE_KEY.put("citybike.alquiler", "citybike.alquiler");

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

                            try {
                                procesarEvento(routingKey, contentType, contenido);
                            } catch (IOException | RepositorioException | EntidadNoEncontrada e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            channel.basicAck(deliveryTag, false);
                        }
                    });

        }
    }

    @Override
    public void publicarEvento(Evento evento) throws IOException {
        String finalRoutingKey = ROUTING_KEY + "." + evento.getIdEvento();
        Gson g = new Gson();
        String mensaje = g.toJson(evento);

        channel.basicPublish(EXCHANGE_NAME, finalRoutingKey,
                new AMQP.BasicProperties.Builder()
                        .contentType("application/json")
                        .build(),
                mensaje.getBytes());
    }

    @Override
    public void procesarEvento(String routingKey, String contentType, String contenido)
            throws JsonParseException, JsonMappingException, IOException, RepositorioException, EntidadNoEncontrada {
        // extract eventId from routingKey (last part of routingKey by ".")
        String[] routingKeyParts = routingKey.split("\\.");
        String idEvento = routingKeyParts[routingKeyParts.length - 1];

        // if content type is application/json, parse the content to a dictionary
        if (contentType.equals("application/json")) {
            // process contenido as json

            Gson g = new Gson();
            switch (idEvento) {
                case "bicicleta-desactivada":
                    EventoBiciDesactivada evento = g.fromJson(contenido, EventoBiciDesactivada.class);
                    String idBici = evento.getIdBici();
                    servicioAlquileres.eliminarReservaDeBici(idBici);
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
