package br.org.venturus.dumpalignment.bean;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.org.venturus.dumpalignment.utils.connect.SSLConnect;
import br.org.venturus.dumpalignment.utils.session.SessionUtils;
import br.org.venturus.dumpalignment.controller.LoginController;

/**ManagedBean da página Login.xhtml, onde o usuário deve se autenticar.
 * 
 * @author vntalgr
 * 
 * @version 1.0
 *
 */
@ManagedBean(name="loginBean")
@RequestScoped
public class LoginBean implements Serializable {
	
	/**
	 * https://mhweb.ericsson.se/mhweb/servlet/servletSearchCorrections?block=rcech&productnr=CAAZA 107 1609&type=EC&show=ENTIRE
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String password;
	private String message;	
	private static final String url = "https://mhweb.ericsson.se/mhweb/servlet/servletCorrView?";
	private static final String query = "corrid=R01MCXJN-8594";
	private boolean isLoggedIn = false;	 	
	
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMessage() {
		return message;
	}	

	/** Método que cria uma conexão com MHWeb afim de validar o usuário e senha.
	 * 
	 * <a href="https://mhweb.ericsson.se/" />
	 * 
	 * @return int - 200 se estiver válido e 401 se usuário ou senha for inválido, outros valores redirecionará para página de erro.
	 */
	private int pingMHWeb() {
		
		SSLConnect connect = new SSLConnect();
		
		
		connect.setLogin(login);
		connect.setPassword(password);
		connect.setUrl(url);
		message = connect.receiveHtmlPage(query);
		
		return connect.getResponseCode();
	}//fim do método
	
	private void registerUser(String user) {
		LoginController loginController = new LoginController();
		try {
			loginController.getUserLogged(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Método que dá acesso ao sistema.
	 * 
	 * @return String - Redireciona o usuário
	 */
	public String doLogin() {	
		
		FacesContext context = FacesContext.getCurrentInstance();		
		
		int response = pingMHWeb();
				
		if (response == SSLConnect.AUTHENTICATION_ERROR) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Login or password invalid..."));
    		return null;			
		} else if(response != SSLConnect.SUCCESS) {
			//errorMessage = loginSys.getMessage();			
			return "error.xhtml";
		}//fim do if-else
		
		SessionUtils.setAttribute("isLoggedIn", isLoggedIn = true);
		SessionUtils.setAttribute("login", this.login);
		SessionUtils.setAttribute("password", this.password);
		
		return "/core/index.xhtml?faces-redirect=true";
	}//fim do método
	
	/**Método que libera memória do servidor removendo os beans
	 * 
	 * @return String - página de login
	 */
	public String doLogout() {  	    
	    //Remove todos os beans da sessão  		
		SessionUtils.doLogout();
		return "/logout.xhtml?faces-redirect=true";
	}//fim do método 
	
	
	
}//fim da classe
