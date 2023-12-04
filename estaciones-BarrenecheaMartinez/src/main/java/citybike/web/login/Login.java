//package citybike.web.login;
//
//import javax.enterprise.context.SessionScoped;
//import javax.faces.context.FacesContext;
//import javax.inject.Inject;
//import javax.inject.Named;
//
//@Named
//@SessionScoped
//public class Login {
//	private String username;
//	private String password;
//	private String role;
//	
//	
//	
//	@Inject
//    protected FacesContext facesContext;
//
//	public void doLogin() {
//		facesContext.getExternalContext().getSessionMap().put("username", username);
//		facesContext.getExternalContext().getSessionMap().put("role", role);
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//
//
//	public String getPassword() {
//		return password;
//	}
//
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	 
//}
