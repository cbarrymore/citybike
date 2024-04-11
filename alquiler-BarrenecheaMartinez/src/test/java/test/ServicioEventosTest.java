package test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import citybike.eventos.dtos.EventoBiciAlquilada;
import citybike.eventos.dtos.EventoBiciDesactivada;
import citybike.eventos.servicio.IServicioEventos;
import citybike.eventos.servicio.ServicioEventosRabbit;

public class ServicioEventosTest {

    private static IServicioEventos servicioEventos;

    @BeforeAll
    public static void obtenerServicio()
            throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
        servicioEventos = new ServicioEventosRabbit();
    }

    @Test
    public void testPublicarEvento() throws IOException {
        EventoBiciAlquilada eventInfo = new EventoBiciAlquilada("idBici", "12-12-2020 12:00:00");
        EventoBiciDesactivada evento = new EventoBiciDesactivada("3", "era fea", "12-12-2020 12:00:00");
        servicioEventos.publicarEvento(eventInfo);
    }

}
