package citybike.servicio;

import java.io.IOException;

import com.google.gson.internal.GsonBuildConfig;

import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServicioEstacionesRetrofit implements IServicioEstaciones {
	Retrofit retrofit;
	IRestEstacionesRetrofit restEstaciones;

	public ServicioEstacionesRetrofit() {
		retrofit = new Retrofit.Builder()
				.baseUrl("http://localhost:4040/api/estaciones")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
		restEstaciones = retrofit.create(IRestEstacionesRetrofit.class);
	}

	@Override
	public boolean huecoDisponible(String idEstacion)
			throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException {
		Response<Boolean> respuesta = restEstaciones.huecoDisponible(idEstacion).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioEstacionesException("Error al comprobar hueco disponible\n" + respuesta.body());
		}
		return respuesta.body();
	}

	@Override
	public void estacionarBici(String idBici, String idEstacion)
			throws RepositorioException, EntidadNoEncontrada, IOException, ServicioEstacionesException {
		Response<Void> respuesta = restEstaciones.estacionarBici(idBici, idEstacion).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioEstacionesException("Error al estacionar bici\n" + respuesta.body());
		}
	}

}
