package pasarela.usuarios.servicio;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface IRestUsuariosRetrofit {
	@GET("/api/usuarios/verificar/{idUsuario}")
    Call<Boolean> verificarUsuario(@Path("idUsuario") String idUsuario, @Body String acceso);
	
	
	@GET("/api/usuarios/verificar/OAuth2/{idUsuario}")
    Call<Boolean> verificarUsuarioOAuth2(@Path("idUsuario") String idUsuario, @Body String acceso);
	
	
    @POST("/api/usuarios")
    Call<UsuarioDto> darAlta(@Body String Id, @Body String Nombre, @Body String Acceso, @Body String Codigo);
}
