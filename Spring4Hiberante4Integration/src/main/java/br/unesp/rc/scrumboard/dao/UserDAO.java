package br.unesp.rc.scrumboard.dao;

import br.unesp.rc.scrumboard.beans.User;

/**
 * Interface de acesso a dados para usuário.
 * @author Vinicius
 */
public interface UserDAO extends DAO<User>{
    public User get(String email);
    public User createUser(User user);
    public void delete(String email);
}
