package br.org.venturus.tambor.utils.dao;

import java.util.List;

import br.org.venturus.tambor.model.Participant;

public interface ParticipantDao extends GenericDao<Participant> {	
	
	public Participant findById(String id, boolean lock);
	
	public List<Participant> findSpocByArea(int areaId);
	
}
