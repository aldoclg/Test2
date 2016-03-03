
package br.org.venturus.dumpalignment.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.catalina.User;

import com.sun.istack.internal.logging.Logger;
import com.tb.client.Logs;
import com.tb.client.SendLog;

/**
 * The Class LoginController.
 */
public class LoginController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The _logged user. */
	private String _loggedUser;
	
	/** The _password. */
	private User _password;
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public User getPassword() {		
		return _password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword( final User password ) {	
		this._password = password;
	}	

	/**
	 * Gets the logged user.
	 *
	 * @return the logged user
	 */
	public String getLoggedUser() {
		return _loggedUser;
	}

	/**
	 * Sets the logged user.
	 *
	 * @param loggedUser the new logged user
	 */
	public void setLoggedUser( final String loggedUser ) {
		this._loggedUser = loggedUser;
	}

	/**
	 * Gets the user logged.
	 *
	 * @param loginSys the login sys
	 * @param passwordSys the password sys
	 * @return the user logged
	 * @throws Exception the exception
	 */
	public void getUserLogged(String loginSys) throws Exception {	

		_loggedUser =  loginSys;

		Date date = new Date();

		Logger.getLogger( LoginController.class ).info( "User logged:" + _loggedUser + " Date: " + date.toString() + " Authorities: " + _loggedUser );		

		System.err.println( "User logged:" + _loggedUser + " Date: " + date.toString() + " Authorities: " + _loggedUser );
					
		Logs logs = new Logs();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			
			logs.setDate(dateFormat.format(date));

			logs.setTool(9); 

			logs.setUser(loginSys);

			logs.setLog(12);  //Mudar esse número (ID do TypeLog)
			
			logs.setValue(loginSys);
	
			String link = "http://146.250.180.218:8080/Tool_Box/rest/servers/createProject";
	
			SendLog.send(link,logs);
				
		} catch(Exception e) {
			e.getCause();
		}
				
		System.out.println("Date : " + date);
		System.out.println("logs.getUser() : " + logs.getUser());
		System.out.println("logs : " + logs);
		System.out.println("value: " + logs.getValue());
		System.out.println("log: " + logs.getLog());
		System.out.println("tool: " + logs.getTool());
		
	}//fim do método
	
}//fim da classe
