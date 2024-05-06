package pasarela.usuarios.servicio;

import java.util.Map;

public interface IServicioUsuarios {
	public Map<String, String> verificarUsuario(String idUsuario, String acceso);
	
	public Map<String, String> verificarUsuarioOAuth2(String idUsuario, String acceso);
	
	public UsuarioDto darAlta(String Id, String Nombre, String Acceso, String Codigo);
}
