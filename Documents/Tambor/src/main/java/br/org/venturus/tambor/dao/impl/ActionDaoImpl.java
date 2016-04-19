package br.org.venturus.tambor.dao.impl;

import javax.persistence.EntityManager;

import br.org.venturus.tambor.model.Action;
import br.org.venturus.tambor.utils.dao.ActionDao;

public class ActionDaoImpl extends GenericDaoImpl<Action> implements ActionDao {	


	public ActionDaoImpl(EntityManager em) {
		super(em);		
	}

}
