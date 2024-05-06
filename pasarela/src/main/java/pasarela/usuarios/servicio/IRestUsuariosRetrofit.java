package pasarela.usuarios.servicio;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface IRestUsuariosRetrofit {
	@GET("/api/usuarios/verificar/{username}/{password}")
    Call<Map<String,String>> verificarUsuario(@Path("username") String idUsuario, @Path("password") String acceso);
	
	
	@GET("/api/usuarios/verificar/OAuth2/{idOauth2}")
    Call<Map<String,String>> verificarUsuarioOAuth2(@Path("idOauth2") String idOauth2);
	
	
    @POST("/api/usuarios")
    Call<Void> darAlta(@Body NuevoUsuarioDTO usuario);
}
