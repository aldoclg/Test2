package br.org.venturus.tambor.dao.impl;

import javax.persistence.EntityManager;

import br.org.venturus.tambor.model.Status;
import br.org.venturus.tambor.utils.dao.StatusDao;

public class StatusDaoImpl extends GenericDaoImpl<Status> implements StatusDao {

	public StatusDaoImpl(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

}
