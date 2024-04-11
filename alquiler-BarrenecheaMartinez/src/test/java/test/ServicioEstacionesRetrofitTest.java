package test;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import citybike.estaciones.servicio.ServicioEstacionesException;
import citybike.estaciones.servicio.ServicioEstacionesRetrofit;
import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;

public class ServicioEstacionesRetrofitTest {
    private ServicioEstacionesRetrofit servicioEstaciones;

    @BeforeEach
    public void setUp() {
        servicioEstaciones = new ServicioEstacionesRetrofit();
    }

    @Test
    public void testHuecoDisponible()
            throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException {
        boolean result = servicioEstaciones.huecoDisponible("660fbce3f9714d18567aa59e");
        System.out.println(result);
    }

    @Test
    public void testEstacionarBici() {
        try {
            servicioEstaciones.estacionarBici("660fbd9ef9714d18567aa5a3", "660fbce3f9714d18567aa59e");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}