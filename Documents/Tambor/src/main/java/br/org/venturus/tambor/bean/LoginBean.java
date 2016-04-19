package br.org.venturus.tambor.bean;

import java.io.Serializable;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;





import com.sun.istack.internal.logging.Logger;

import br.org.venturus.tambor.model.Area;
import br.org.venturus.tambor.model.Participant;
import br.org.venturus.tambor.model.User;
import br.org.venturus.tambor.utils.connect.SSLConnect;
import br.org.venturus.tambor.utils.dao.ParticipantDao;
import br.org.venturus.tambor.utils.jpa.JPAUtils;
import br.org.venturus.tambor.utils.model.Profile;
import br.org.venturus.tambor.utils.parser.ParserUserProfile;
import br.org.venturus.tambor.utils.session.SessionUtils;
import br.org.venturus.tambor.controller.LoginController;
import br.org.venturus.tambor.dao.impl.AreaDaoImpl;
import br.org.venturus.tambor.dao.impl.ParticipantDaoImpl;
import br.org.venturus.tambor.dao.impl.UserDaoImpl;

/**ManagedBean da p�gina Login.xhtml, onde o usu�rio deve se autenticar.
 * 
 * @author vntalgr
 * 
 * @version 1.0
 *
 */
@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {
	
	private static final long serialVersionUID = 5304838337270726059L;
	/**
	 * https://mhweb.ericsson.se/mhweb/servlet/servletSearchCorrections?block=rcech&productnr=CAAZA 107 1609&type=EC&show=ENTIRE
	 */
	private String login;
	private String password;
	private String message;	
	private static final String url = "https://mhweb.ericsson.se/mhweb/servlet/UserView?action=FETCHUSERINFORMATION&corporateid=";	
	private boolean isLoggedIn = false;	 
	private ParticipantDaoImpl participantDao;
	private UserDaoImpl userDao;
	private Participant participant;
	private User user;
	
	@PostConstruct
	public void init() {
		userDao = new UserDaoImpl(JPAUtils.getEntityManager());
		participantDao = new ParticipantDaoImpl(JPAUtils.getEntityManager());
	}
	
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
		message = connect.receiveHtmlPage(login).replaceAll(">\\s+<", "><");
		
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
		
		int response = pingMHWeb();
				
		if (response == SSLConnect.AUTHENTICATION_ERROR) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Login or password invalid..."));
    		return null;			
		} else if(response != SSLConnect.SUCCESS) {
			//errorMessage = loginSys.getMessage();			
			return "error.xhtml?faces-redirect=true";
		}//fim do if-else	
		
		if (SessionUtils.getAttribute("isLoggedIn") == null) {
		
			checkUser();	
		
			SessionUtils.setAttribute("isLoggedIn", isLoggedIn = true);
			SessionUtils.setAttribute("login", login);
			SessionUtils.setAttribute("user", user);
		
			if (participant != null) {
				SessionUtils.setAttribute("isParticipant", true);
				SessionUtils.setAttribute("participant", participant);
			} else {
				SessionUtils.setAttribute("isParticipant", false);
			}//fim do if-else
		
			//registerUser(login);
			Logger.getLogger(LoginBean.class.getClass()).log(Level.INFO, this.login);
		}
		
		return "/core/createTicket.xhtml?faces-redirect=true";
	}//fim do m�todo
	
	/**
	 * Procura usu�rio no BD, se n�o encontrar adiciona no BD
	 * 
	 */
	private void checkUser() {
		 //userDao.findByHQL("select u from User u where ericsson_id = " + login);
		if ((user = userDao.findById(login, false)) == null) {	
			user = new User();
			user.setIdEricsson(login);
			user.setEmail(ParserUserProfile.getEmail(message));
			user.setName(ParserUserProfile.getName(message));
			userDao.insert(user);			
		} else {
			participant = participantDao.findById(login, false);
		}//fim do if-else
		
	}//fim do m�todo		
	
	/**M�todo que libera mem�ria do servidor removendo os beans
	 * 
	 * @return String - p�gina de login
	 */
	public String doLogout() {  	    
	    //Remove todos os beans da sess�o  		
		SessionUtils.doLogout();
		return "/login.xhtml?faces-redirect=true";
	}//fim do m�todo 
	
	
	
}//fim da classe
