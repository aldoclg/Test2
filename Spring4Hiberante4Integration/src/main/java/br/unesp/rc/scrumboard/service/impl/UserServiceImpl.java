package br.unesp.rc.scrumboard.service.impl;
import br.unesp.rc.scrumboard.beans.User;
import br.unesp.rc.scrumboard.dao.DAO;
import br.unesp.rc.scrumboard.dao.UserDAO;
import br.unesp.rc.scrumboard.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da interface do serviço de usuários
 * @author Vinicius
 */
@Service
@Transactional
public class UserServiceImpl extends BaseService<User> implements UserService{

    public UserServiceImpl() {
    }
    
    @Autowired
    private UserDAO userDAO;
    
    @Override
    public DAO<User> getDAO() {
        return this.userDAO;
    }
    
    @Override
    public boolean isValid(User user) {
        User userData = get(user.getEmail());
        return user.equals(userData);
    }

    @Override
    public User get(String email){
        return userDAO.get(email);
    }

    @Override
    public void delete(String email) {
        userDAO.delete(email);
    }
}
