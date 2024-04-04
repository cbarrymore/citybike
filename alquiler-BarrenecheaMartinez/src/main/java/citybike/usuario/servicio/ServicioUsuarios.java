package usuario.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServicioUsuarios implements IServicioUsuarios {
	public static class UsuarioAutenticacion {
		private String id;
		private String usuario;
		private String contraseña;
		private String roles;
// Métodos get y set
		public String getUsuario() {
			return usuario;
		}
		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}
		public String getContraseña() {
			return contraseña;
		}
		public void setContraseña(String contraseña) {
			this.contraseña = contraseña;
		}
		public String getRoles() {
			return roles;
		}
		public void setRoles(String roles) {
			this.roles = roles;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
	}
	
	private static String USUARIO = "usuario";
	private static String GESTOR = "gestor";
	private List<UsuarioAutenticacion> usuarios;
	public ServicioUsuarios() {
		this.usuarios = new ArrayList<UsuarioAutenticacion>();
		UsuarioAutenticacion usuario = new UsuarioAutenticacion();
		usuario.setUsuario("usuario");
		usuario.setContraseña("contraseña");
		usuario.setRoles(USUARIO);
		usuario.setId("1");
		
		
		UsuarioAutenticacion usuarioGestor= new UsuarioAutenticacion();
		usuarioGestor.setUsuario("usuarioGestor");
		usuarioGestor.setContraseña("contraseña");
		usuarioGestor.setRoles( USUARIO + "," +GESTOR);
		usuarioGestor.setId("2");
		
		usuarios.add(usuario);
		usuarios.add(usuarioGestor);
	}
	@Override
	public Map<String, Object> verificarCredenciales(String usuario, String contraseña) {
		if(usuarios.isEmpty()) {
			UsuarioAutenticacion u = new UsuarioAutenticacion();
			u.setUsuario(usuario);
			u.setContraseña(contraseña);
			u.setRoles(USUARIO);
			usuarios.add(u);
		}
		Optional<UsuarioAutenticacion> usuarioOptional= usuarios.stream()
				.filter(u -> u.getUsuario().equals(usuario))
				.findFirst();
		if(usuarioOptional.isPresent()) {
			Map<String, Object> resultado = new HashMap<String, Object>();
			resultado.put("sub", usuarioOptional.get().getId());
			resultado.put("roles", usuarioOptional.get().getRoles());
			return resultado;
		}
		return null;
	}

}
