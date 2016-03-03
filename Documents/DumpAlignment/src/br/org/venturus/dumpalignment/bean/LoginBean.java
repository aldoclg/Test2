package br.org.venturus.dumpalignment.bean;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.org.venturus.dumpalignment.utils.connect.SSLConnect;
import br.org.venturus.dumpalignment.utils.session.SessionUtils;
import br.org.venturus.dumpalignment.controller.LoginController;

/**ManagedBean da p�gina Login.xhtml, onde o usu�rio deve se autenticar.
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

	/** M�todo que cria uma conex�o com MHWeb afim de validar o usu�rio e senha.
	 * 
	 * <a href="https://mhweb.ericsson.se/" />
	 * 
	 * @return int - 200 se estiver v�lido e 401 se usu�rio ou senha for inv�lido, outros valores redirecionar� para p�gina de erro.
	 */
	private int pingMHWeb() {
		
		SSLConnect connect = new SSLConnect();
		
		
		connect.setLogin(login);
		connect.setPassword(password);
		connect.setUrl(url);
		message = connect.receiveHtmlPage(query);
		
		return connect.getResponseCode();
	}//fim do m�todo
	
	private void registerUser(String user) {
		LoginController loginController = new LoginController();
		try {
			loginController.getUserLogged(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** M�todo que d� acesso ao sistema.
	 * 
	 * @return String - Redireciona o usu�rio
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
	}//fim do m�todo
	
	/**M�todo que libera mem�ria do servidor removendo os beans
	 * 
	 * @return String - p�gina de login
	 */
	public String doLogout() {  	    
	    //Remove todos os beans da sess�o  		
		SessionUtils.doLogout();
		return "/logout.xhtml?faces-redirect=true";
	}//fim do m�todo 
	
	
	
}//fim da classe
