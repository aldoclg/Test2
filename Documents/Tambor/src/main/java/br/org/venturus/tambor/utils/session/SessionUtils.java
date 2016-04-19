package br.org.venturus.tambor.utils.session;

import java.io.Serializable;
import java.util.logging.Level;

import javax.faces.context.FacesContext;

import com.sun.istack.internal.logging.Logger;

public class SessionUtils implements Serializable {	
	
	private static final long serialVersionUID = 1L;

	/**Método que libera memória do servidor removendo os beans e invalidando a session
	 * 
	 * @return String - página de login
	 */
	public static final void doLogout() {  	    
	    //Remove todos os beans da sessão  
		FacesContext context = FacesContext.getCurrentInstance();
		Logger.getLogger(SessionUtils.class.getClass()).log(Level.INFO, (String)SessionUtils.getAttribute("login"));
		for (String bean : context.getExternalContext().getSessionMap().keySet()) { 			
		    context.getExternalContext().getSessionMap().remove(bean);		    
		}//fim do for	
		context.getExternalContext().invalidateSession();
		
	}//fim do método 
	
	/** Método que retorna atributos salvos na sessão
	 * 
	 * @param nome
	 * @return - retorna o valor do atributo
	 */
	public static final Object getAttribute(String nome){
		FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getSessionMap().get(nome);
    }//fim do método
    
	/** Método que inseri atributos à sessão
	 * 
	 * @param nome - 
	 * @param valor 
	 */
    public static final void setAttribute(String nome, Object valor){
    	FacesContext context = FacesContext.getCurrentInstance();
    	if(context.getExternalContext().getSessionMap().containsKey(nome)) {
    		context.getExternalContext().getSessionMap().remove(nome);
    	}
    	context.getExternalContext().getSessionMap().put(nome, valor);
    }//fim do método

}//fim da classe
