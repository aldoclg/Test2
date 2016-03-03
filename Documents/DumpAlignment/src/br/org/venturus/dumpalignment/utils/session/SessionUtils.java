package br.org.venturus.dumpalignment.utils.session;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public class SessionUtils implements Serializable {	
	
	private static final long serialVersionUID = 1L;

	/**M�todo que libera mem�ria do servidor removendo os beans e invalidando a session
	 * 
	 * @return String - p�gina de login
	 */
	public static final void doLogout() {  	    
	    //Remove todos os beans da sess�o  
		FacesContext context = FacesContext.getCurrentInstance();
		
		for (String bean : context.getExternalContext().getSessionMap().keySet()) { 			
		    context.getExternalContext().getSessionMap().remove(bean);
		    System.out.print("\n Remove: " + bean);
		}//fim do for	
		context.getExternalContext().invalidateSession();
		
	}//fim do m�todo 
	
	/** M�todo que retorna atributos salvos na sess�o
	 * 
	 * @param nome
	 * @return - retorna o valor do atributo
	 */
	public static final Object getAttribute(String nome){
		FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getSessionMap().get(nome);
    }//fim do m�todo
    
	/** M�todo que inseri atributos � sess�o
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
    }//fim do m�todo

}//fim da classe
