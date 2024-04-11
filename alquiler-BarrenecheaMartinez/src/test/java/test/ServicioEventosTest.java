package test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import citybike.eventos.servicio.EventosEmitir;
import citybike.eventos.servicio.IServicioEventos;
import citybike.eventos.servicio.ServicioEventosRabbit;
import citybike.servicio.FactoriaServicios;

public class ServicioEventosTest {

    private static IServicioEventos servicioEventos;

    @BeforeAll
    public static void obtenerServicio()
            throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException, TimeoutException {
        servicioEventos = new ServicioEventosRabbit();
    }

    @Test
    public void testPublicarEvento() throws IOException {
        String eventInfo = "Bicicleta alquilada";
        servicioEventos.publicarEvento("citybike.alquiler", EventosEmitir.BICICLETA_ALQUILADA, eventInfo);
    }

}
