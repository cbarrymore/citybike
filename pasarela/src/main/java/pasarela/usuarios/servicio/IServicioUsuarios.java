package pasarela.usuarios.servicio;

import java.util.Map;

public interface IServicioUsuarios {
	public Map<String, String> verificarUsuario(String username, String acceso);
	
	public Map<String, String> verificarUsuarioOAuth2(String idUsuario);
	
	public void darAlta(String Id, String Nombre, String Acceso, String Codigo, boolean oauth2);
}
