package br.org.venturus.tambor.utils.dao;

import br.org.venturus.tambor.model.User;

public interface UserDao extends GenericDao<User> {

	public User findById(String id, boolean lock);

}
