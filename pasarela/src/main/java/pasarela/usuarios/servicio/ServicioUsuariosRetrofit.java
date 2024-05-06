package pasarela.usuarios.servicio;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;

import retrofit2.Response;
import retrofit2.Retrofit;

@Service
@Transient
public class ServicioUsuariosRetrofit implements IServicioUsuarios{
	private Retrofit retrofit;
	private IRestUsuariosRetrofit restUsuarios;
	
	public ServicioUsuariosRetrofit() {
		retrofit = new Retrofit.Builder()
				.baseUrl("http://localhost:4040/")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
		restUsuarios = retrofit.create(IRestUsuariosRetrofit.class);
	}
	
	@Override
	public Map<String, String> verificarUsuario(String username, String acceso) {
		Response<Map<String, String>> respuesta = restUsuarios.verificarUsuario(username, acceso).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioUsuariosException("Error al obtener claims\n" + respuesta.body());
		}
		return respuesta.body();
	}

	@Override
	public Map<String, String> verificarUsuarioOAuth2(String idUsuario) {
		Response<Map<String, String>> respuesta = restUsuarios.verificarUsuarioOAuth2(idUsuario).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioUsuariosException("Error al obtener claims\n" + respuesta.body());
		}
		return respuesta.body();// resolver esto
	}
 
	@Override
	public void darAlta(String id, String nombre, String acceso, String codigo, boolean oauth2){
		Response<Void> respuesta = restUsuarios.darAlta(new NuevoUsuarioDTO(id, id, nombre, acceso, codigo, oauth2)).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioUsuariosException("Error al obtener claims\n" + respuesta.body());
		}
	}


}
