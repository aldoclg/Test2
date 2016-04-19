package br.org.venturus.tambor.dao.impl;

import javax.persistence.EntityManager;

import br.org.venturus.tambor.model.Ticket;
import br.org.venturus.tambor.utils.dao.TicketDao;


public class TicketDaoImpl extends GenericDaoImpl<Ticket> implements TicketDao {

	public TicketDaoImpl(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}	
}
