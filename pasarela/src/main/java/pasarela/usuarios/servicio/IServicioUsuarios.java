package pasarela.usuarios.servicio;

import java.io.IOException;
import java.util.Map;

import pasarela.excepciones.ServicioUsuariosException;

public interface IServicioUsuarios {
	public Map<String, Object> verificarUsuario(String username, String acceso) throws ServicioUsuariosException, IOException;
	
	public Map<String, Object> verificarUsuarioOAuth2(String idUsuario) throws ServicioUsuariosException, IOException;
	
	public void darAlta(String id, String username, String nombre, String acceso, String codigo, boolean oauth2) throws ServicioUsuariosException, IOException;
	
	public String solicitarCodigo(String idUsuario) throws ServicioUsuariosException,IOException; 
}
