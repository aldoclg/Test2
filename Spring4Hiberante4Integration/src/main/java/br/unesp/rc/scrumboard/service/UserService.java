package br.unesp.rc.scrumboard.service;

import br.unesp.rc.scrumboard.beans.User;

/**
 * Interface para o serviço de usuários.
 * @author Vinicius
 */
public interface UserService extends Service<User> {
    public User get(String email);
    public boolean isValid(User user);
    public void delete(String email);
}
