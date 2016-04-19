package br.org.venturus.tambor.service;

import br.org.venturus.tambor.dao.impl.StatusDaoImpl;
import br.org.venturus.tambor.model.Status;
import br.org.venturus.tambor.utils.jpa.JPAUtils;

public class StatusService {	
	
	private static StatusDaoImpl statusDao = new StatusDaoImpl(JPAUtils.getEntityManager());	
	
	public static final Status getStatus(int id) {		
		return statusDao.findById(id, false);
	}
	
	public static final Status getStatusByName(String name) {		
		return statusDao.findByHQL("from Status s where s.status ='" + name + "'").get(0);
	}

}
