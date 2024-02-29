package servicio;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.glassfish.jersey.internal.guava.Lists;

public class ServicioUsuarios implements IServicioUsuarios {
	public static class UsuarioAutenticacion {
		private String id;
		private String usuario;
		private String contraseña;
		private List<String> roles;
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
		public List<String> getRoles() {
			return roles;
		}
		public void setRoles(List<String> roles) {
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
		UsuarioAutenticacion usuario = new UsuarioAutenticacion();
		usuario.setUsuario("usuario");
		usuario.setContraseña("contraseña");
		usuario.setRoles(Arrays.asList(USUARIO));
		usuario.setId("1");
		
		
		UsuarioAutenticacion usuarioGestor= new UsuarioAutenticacion();
		usuarioGestor.setUsuario("usuarioGestor");
		usuarioGestor.setContraseña("contraseña");
		usuarioGestor.setRoles(Arrays.asList(USUARIO,GESTOR));
		usuarioGestor.setId("2");
	}
	@Override
	public Map<String, Object> verificarCredenciales(String usuario, String contraseña) {
		Optional<UsuarioAutenticacion> usuarioOptional= usuarios.stream()
				.filter(u -> u.getUsuario().equals(usuario))
				.findFirst();
		if(usuarioOptional.isPresent()) {
			Map<String, Object> resultado = Map.of(
					"sub", usuarioOptional.get().getId(),
					"roles" , usuarioOptional.get().getRoles()
			);
			return resultado;
		}
		return null;
	}

}
