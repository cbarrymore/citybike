package aadd.estaciones_2_BarrenecheaMartinez;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones2.App;
import estaciones2.estacion.servicio.IServicioEstaciones;
import estaciones2.estacion.servicio.ServicioEstaciones;
import estaciones2.repositorio.RepositorioException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

/**
 * Unit test for simple App.
 */
class AppTest {
    public static void main(String[] args) throws RepositorioException {
        ConfigurableApplicationContext contexto = SpringApplication.run(App.class, args);

        // Access ServicioEstaciones
        IServicioEstaciones servEstaciones = contexto.getBean(IServicioEstaciones.class);
        // da de alta varias estaciones, con valores realistas
        servEstaciones.altaEstacion("Estacion 1", 10, 123456789, new BigDecimal(70), new BigDecimal(40));
        servEstaciones.altaEstacion("Estacion 2", 20, 987654321, new BigDecimal(60), new BigDecimal(50));
        servEstaciones.altaEstacion("Estacion 3", 30, 123456789, new BigDecimal(30), new BigDecimal(10));

        contexto.close();
    }
}
