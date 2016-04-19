package br.org.venturus.tambor.service;

import br.org.venturus.tambor.model.Action;
import br.org.venturus.tambor.model.User;
import br.org.venturus.tambor.utils.model.EventType;

public final class ActionService {
	
	/**
	 * Gera um objeto Action
	 * @param e
	 * @return
	 */
	public static final Action generateAction(EventType e, User user, String value) {
		
		Action action = new Action();			
		action.setEvent(e);
		action.setUser(user);
		action.setValue(value + user.getIdEricsson());
		return action;
	}//fim do método
	

}
