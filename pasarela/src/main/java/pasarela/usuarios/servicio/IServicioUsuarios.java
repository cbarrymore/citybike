package pasarela.usuarios.servicio;

import java.io.IOException;
import java.util.Map;

public interface IServicioUsuarios {
	public Map<String, String> verificarUsuario(String username, String acceso) throws ServicioUsuariosException, IOException;
	
	public Map<String, Object> verificarUsuarioOAuth2(String idUsuario) throws ServicioUsuariosException, IOException;
	
	public void darAlta(String Id, String Nombre, String Acceso, String Codigo, boolean oauth2) throws ServicioUsuariosException, IOException;
	
	public String solicitarCodigo(String idUsuario) throws ServicioUsuariosException,IOException; 
}
