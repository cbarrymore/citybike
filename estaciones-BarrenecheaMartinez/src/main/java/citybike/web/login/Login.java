package citybike.web.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class Login implements Serializable{
	private String username;
	private String password;
	private String role;
	
	
	
	@Inject
    protected FacesContext facesContext;

	public void doLogin() {
		System.out.println("Acabo de hacer login");
		facesContext.getExternalContext().getSessionMap().put("username", username);
		facesContext.getExternalContext().getSessionMap().put("role", role);
	}

	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	 
}
