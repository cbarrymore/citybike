package pasarela.usuarios.servicio;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Service
@Transient
public class ServicioUsuariosRetrofit implements IServicioUsuarios{
	private Retrofit retrofit;
	private IRestUsuariosRetrofit restUsuarios;
	
	public ServicioUsuariosRetrofit() {
		retrofit = new Retrofit.Builder()
				.baseUrl("http://localhost:5115/")
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
		restUsuarios = retrofit.create(IRestUsuariosRetrofit.class);
	}
	
	@Override
	public Map<String, String> verificarUsuario(String username, String acceso) throws ServicioUsuariosException, IOException {
		Response<Map<String, String>> respuesta = restUsuarios.verificarUsuario(username, acceso).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioUsuariosException("Error al obtener claims\n" + respuesta.body());
		}
		return respuesta.body();
	}

	@Override
	public Map<String, Object> verificarUsuarioOAuth2(String idUsuario) throws ServicioUsuariosException, IOException {
		Response<Map<String, Object>> respuesta = restUsuarios.verificarUsuarioOAuth2(idUsuario).execute();
		int responseCode = respuesta.code();
		if (!respuesta.isSuccessful()) {
			throw new ServicioUsuariosException("Error al obtener claims\n" + respuesta.body());
		}
		if (responseCode == 404) {
			return null;
		}
		return respuesta.body();// resolver esto
	}
 
	@Override
	public void darAlta(String id, String nombre, String acceso, String codigo, boolean oauth2) throws ServicioUsuariosException, IOException{
		Response<Void> respuesta = restUsuarios.darAlta(new NuevoUsuarioDTO(id, id, nombre, acceso, codigo, oauth2)).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioUsuariosException("Error al obtener claims\n" + respuesta.body());
		}
	}

	@Override
	public String solicitarCodigo(String idUsuario) throws ServicioUsuariosException, IOException {
		Response<String> respuesta = restUsuarios.solicitarCodigo(idUsuario).execute();
		if(!respuesta.isSuccessful()){
			throw new ServicioUsuariosException("Error al solicitar código\n" + respuesta.body()); 
		} 
		return null;
	}


}
