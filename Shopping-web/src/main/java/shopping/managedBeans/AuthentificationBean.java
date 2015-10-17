package shopping.managedBeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import shopping.model.Admin;
import shopping.model.User;
import shopping.service.AuthenticationServiceLocal;

@ManagedBean(name = "authBean")
@SessionScoped
public class AuthentificationBean {
	private String identifiant;
	private String motDePasse;
	private boolean loggedIn = false;
	private String userType = "";

	@EJB
	private AuthenticationServiceLocal localEjb;
	private User user = new User();

	
	
	
	public String doLogin() {
		String navto = null;
		User found;
		found = localEjb.authenticate(user.getLogin(), user.getPassword());
		if (found != null) {
			if (found instanceof Admin) {
				loggedIn = true;
				user = found;
				userType = "admin";
				return "/pages/admin/home";
			}
			loggedIn = true;
			user = found;
			return navto;
		}
		FacesMessage message = new FacesMessage("bad credantial!");
		FacesContext.getCurrentInstance().addMessage("form_login:from_submit",
				message);
		return navto;
	}

	public String doLogout() {
		loggedIn = false;
		user = new User();
		userType = "";
		return "/pages/login";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AuthentificationBean() {
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
