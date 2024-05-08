package pasarela.usuarios.servicio;


import java.util.Map;

import pasarela.usuarios.dto.ContenedorCodigo;
import pasarela.usuarios.dto.NuevoUsuarioDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface IRestUsuariosRetrofit {
	@GET("/api/usuarios/verificar/{username}/{password}")
    Call<Map<String,Object>> verificarUsuario(@Path("username") String idUsuario, @Path("password") String acceso);
	
	
	@GET("/api/usuarios/verificar/OAuth2/{idOauth2}")
    Call<Map<String,Object>> verificarUsuarioOAuth2(@Path("idOauth2") String idOauth2);
	
	
    @POST("/api/usuarios")
    Call<Void> darAlta(@Body NuevoUsuarioDTO usuario);
    
    @GET("/api/usuarios/solicitud/{idUsuario}")
    Call<ContenedorCodigo> solicitarCodigo(@Path("idUsuario") String idUsuario);
}
