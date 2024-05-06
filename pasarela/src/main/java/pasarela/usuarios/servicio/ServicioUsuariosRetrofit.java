package pasarela.usuarios.servicio;

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
	public Map<String, String> verificarUsuario(String idUsuario, String acceso) {
		Response<Boolean> respuesta = restUsuarios.verificarUsuario(idEstacion).execute();
		if (!respuesta.isSuccessful()) {
			throw new ServicioEstacionesException("Error al comprobar hueco disponible\n" + respuesta.body());
		}
		return respuesta.body();
	}

	@Override
	public Map<String, String> verificarUsuarioOAuth2(String idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioDto darAlta(String Id, String Nombre, String Acceso, String Codigo) {
		// TODO Auto-generated method stub
		return null;
	}

}
