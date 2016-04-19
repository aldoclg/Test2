package br.org.venturus.tambor.service;

import java.util.List;

import br.org.venturus.tambor.dao.impl.AreaDaoImpl;
import br.org.venturus.tambor.model.Area;
import br.org.venturus.tambor.utils.jpa.JPAUtils;

public class AreaService {
	
	private static AreaDaoImpl areaDao = new AreaDaoImpl(JPAUtils.getEntityManager());
	
	public static final List<Area> getAreas() {
		return areaDao.findAll();
	}

}
