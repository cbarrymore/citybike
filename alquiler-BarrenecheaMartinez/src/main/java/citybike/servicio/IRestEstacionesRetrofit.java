package citybike.servicio;

import citybike.repositorio.EntidadNoEncontrada;
import citybike.repositorio.RepositorioException;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRestEstacionesRetrofit {
    @GET("/{idEstacion}/hueco")
    Call<Boolean> huecoDisponible(@Path("idEstacion") String idEstacion)
            throws RepositorioException, EntidadNoEncontrada;

    @PUT("/{idEstacion}/bicis/{idBici}")
    Call<Void> estacionarBici(@Path("idBici") String idBici, @Path("idEstacion") String idEstacion);
}
