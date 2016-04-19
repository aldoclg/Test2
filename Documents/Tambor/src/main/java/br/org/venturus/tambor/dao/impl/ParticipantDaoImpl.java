package br.org.venturus.tambor.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import br.org.venturus.tambor.model.Participant;
import br.org.venturus.tambor.utils.dao.ParticipantDao;

public class ParticipantDaoImpl extends GenericDaoImpl<Participant> implements ParticipantDao, Serializable {
	
	private static final long serialVersionUID = -5017573018036171288L;
	private EntityManager em;

	public ParticipantDaoImpl(EntityManager em) {		
		super(em);		
		this.em = em;
	}

	public Participant findById(String id, boolean lock) {
		if (lock)
			return (Participant) em.find(Participant.class, id, LockModeType.READ);
		else
			return (Participant) em.find(Participant.class, id);
	}

	@Override
	@Deprecated
	public Participant findById(int id, boolean lock) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Participant> findSpocByArea(int areaId) {		
		return findByHQL("from Participant p where p.profile = 1 and p.area = " + areaId);
	}

}
