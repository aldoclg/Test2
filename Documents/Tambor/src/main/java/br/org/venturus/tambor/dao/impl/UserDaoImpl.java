package br.org.venturus.tambor.dao.impl;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import br.org.venturus.tambor.model.User;
import br.org.venturus.tambor.utils.dao.UserDao;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao, Serializable {
	
	private static final long serialVersionUID = -4798206317091108850L;
	private EntityManager em;

	public UserDaoImpl(EntityManager em) {
		super(em);
		this.em = em;
	}
	
	public User findById(String id, boolean lock) {
		if (lock)
			return (User) em.find(User.class, id, LockModeType.READ);
		else
			return (User) em.find(User.class, id);
	}

	@Override
	@Deprecated
	public User findById(int id, boolean lock) {
		// TODO Auto-generated method stub
		return null;
	}
}
