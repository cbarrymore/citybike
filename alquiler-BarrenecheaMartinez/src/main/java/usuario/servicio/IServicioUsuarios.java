package servicio;

import java.util.Map;

public interface IServicioUsuarios {
	
	public Map<String,Object> verificarCredenciales(String usuario, String contraseña);
}
