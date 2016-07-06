package br.unesp.rc.scrumboard.dao.impl;

import br.unesp.rc.scrumboard.beans.User;
import br.unesp.rc.scrumboard.dao.UserDAO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da interface de acesso a dados
 *
 * @author Vinicius
 */
@Repository
@Transactional
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {

    public UserDAOImpl() {
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }

    @Override
    public String getTableName() {
        return "User";
    }

    @Override
    public List<String> getTableFields() {
        List<String> fields = new ArrayList<>();
        fields.add("email");
        fields.add("name");
        fields.add("password");
        return fields;
    }

    @Override
    public User populateObject(Object[] object) {
        String email = (String) object[0];
        String username = (String) object[1];
        String pass = (String) object[2];
        return new User(email, username, pass);
    }

    @Override
    public String getSearchNameField() {
        return "name";
    }

    @Override
    public User get(String email) {
        return hibernateUtil.fetchById(email, getType());
    }

    @Override
    public User createUser(User user) {
        hibernateUtil.create(user);
        return user;
    }

    @Override
    public void delete(String email) {
        User user = get(email);
        hibernateUtil.delete(user);
    }
}
