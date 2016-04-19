package br.org.venturus.tambor.service;

import br.org.venturus.tambor.dao.impl.ParticipantDaoImpl;
import br.org.venturus.tambor.model.Area;
import br.org.venturus.tambor.model.Participant;
import br.org.venturus.tambor.utils.jpa.JPAUtils;

public class ParticipantService {	
	
	private static ParticipantDaoImpl participantDao = new ParticipantDaoImpl(JPAUtils.getEntityManager());	
	
	public static final Participant getSpoc(Area area) {
		return participantDao.findSpocByArea(area.getId()).get(0);
	}

}
