package aadd.estaciones_2_BarrenecheaMartinez;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import estaciones2.App;
import estaciones2.estacion.servicio.IServicioEstaciones;
import estaciones2.estacion.servicio.ServicioEstaciones;
import estaciones2.repositorio.EntidadNoEncontrada;
import estaciones2.repositorio.RepositorioException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

/**
 * Unit test for simple App.
 */
class AppTest {
        public static void main(String[] args) throws RepositorioException,
                        EntidadNoEncontrada {
                // ConfigurableApplicationContext contexto = SpringApplication.run(App.class,
                // args);

                // IServicioEstaciones servEstaciones =
                // contexto.getBean(IServicioEstaciones.class);

                // String idEstacion1 = servEstaciones.altaEstacion("Estacion 1", 10, 123456789,
                // new BigDecimal(70),
                // new BigDecimal(40));
                // String idEstacion2 = servEstaciones.altaEstacion("Estacion 2", 20, 987654321,
                // new BigDecimal(60),
                // new BigDecimal(50));
                // String idEstacion3 = servEstaciones.altaEstacion("Estacion 3", 30, 123456789,
                // new BigDecimal(30),
                // new BigDecimal(10));

                // assertEquals(idEstacion1,
                // servEstaciones.obtenerEstacion(idEstacion1).getId());

                // // añadir bicis a las estaciones
                // String idBici1 = servEstaciones.altaBici("Modelo 1", idEstacion1);
                // // servEstaciones.altaBici("Modelo 2", idEstacion1);
                // // servEstaciones.altaBici("Modelo 3", idEstacion1);
                // String idBici2 = servEstaciones.altaBici("Modelo 4", idEstacion2);
                // // servEstaciones.altaBici("Modelo 5", idEstacion2);
                // // servEstaciones.altaBici("Modelo 6", idEstacion2);
                // // servEstaciones.altaBici("Modelo 7", idEstacion3);
                // String idBici3 = servEstaciones.altaBici("Modelo 8", idEstacion3);

                // servEstaciones.darBajaBici(idBici2, "Baja por usuario");

                // contexto.close();
        }
}
