package br.org.venturus.tambor.dao.impl;

import javax.persistence.EntityManager;

import br.org.venturus.tambor.model.Area;
import br.org.venturus.tambor.utils.dao.AreaDao;

public class AreaDaoImpl extends GenericDaoImpl<Area> implements AreaDao {
	
	public AreaDaoImpl(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

}
