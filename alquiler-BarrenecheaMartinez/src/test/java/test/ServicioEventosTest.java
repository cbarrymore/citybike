package test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import citybike.eventos.dtos.EventoBiciAlquilada;
import citybike.eventos.dtos.EventoBiciAlquilerConcluido;
import citybike.eventos.dtos.EventoBiciDesactivada;
import citybike.eventos.servicio.IServicioEventos;
import citybike.eventos.servicio.ServicioEventosRabbit;

public class ServicioEventosTest {
/* 
    private static IServicioEventos servicioEventos;

    @BeforeAll
    public static void obtenerServicio()
            throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
        servicioEventos = new ServicioEventosRabbit();
    }

    @Test
    public void testPublicarEventoBiciAlquilerConcluido() throws IOException {
        EventoBiciAlquilerConcluido eventInfo = new EventoBiciAlquilerConcluido("idBici",
                LocalDateTime.now().toString());
        servicioEventos.publicarEvento(eventInfo);
    }

    @Test
    public void testPublicarEventoBiciAlquilada() throws IOException {
        EventoBiciAlquilada eventInfo = new EventoBiciAlquilada("idBici", LocalDateTime.now().toString());
        servicioEventos.publicarEvento(eventInfo);
    }

    @Test
    public void testRecibirEventoBiciDesactivada() throws IOException {
        EventoBiciDesactivada eventInfo = new EventoBiciDesactivada("idBici", "motivo", LocalDateTime.now().toString());
        servicioEventos.publicarEvento(eventInfo);
    }
    Desactivado por actualizacion*/
}
