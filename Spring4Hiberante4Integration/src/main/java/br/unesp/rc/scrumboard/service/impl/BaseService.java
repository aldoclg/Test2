package br.unesp.rc.scrumboard.service.impl;

import br.unesp.rc.scrumboard.dao.DAO;
import br.unesp.rc.scrumboard.service.Service;
import java.util.List;

/**
 * Serviço base para todos os outros
 * @author Vinicius
 */
public abstract class BaseService<T> implements Service<T> {

    public abstract DAO<T> getDAO();
    
    @Override
    public void create(T entity) {
        getDAO().create(entity);
    }

    @Override
    public T update(T entity) {
        return getDAO().update(entity);
    }

    @Override
    public void delete(long id) {
        getDAO().delete(id);
    }

    @Override
    public List<T> getAll() {
        return getDAO().getAll();
    }

    @Override
    public T get(long id) {
        return getDAO().get(id);
    }

    @Override
    public List<T> getByName(String name) {
        return getDAO().getByName(name);
    }
    
}
