package estaciones2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("estacion.2.bici.repositorio")
public class App {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
		}

}
