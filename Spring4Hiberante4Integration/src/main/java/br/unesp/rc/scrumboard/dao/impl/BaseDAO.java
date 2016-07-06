package br.unesp.rc.scrumboard.dao.impl;

import br.unesp.rc.scrumboard.dao.DAO;
import br.unesp.rc.scrumboard.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe base para DAO.
 * @author Vinicius
 */
@Repository
@Transactional
public abstract class BaseDAO<T> implements DAO<T> {

    @Autowired
    protected HibernateUtil hibernateUtil;
    
    public abstract Class<T> getType();
    public abstract String getTableName();
    public abstract List<String> getTableFields();
    public abstract T populateObject(Object[] object);
    public abstract String getSearchNameField();
    
    @Override
    public void create(T entity) {
        hibernateUtil.create(entity);
    }

    @Override
    public T update(T entity) {
        return hibernateUtil.update(entity);
    }

    @Override
    public void delete(long id) {
        T entity = get(id);
        hibernateUtil.delete(entity);
    }

    @Override
    public List<T> getAll() {
        return hibernateUtil.fetchAll(getType());
    }

    @Override
    public T get(long id) {
        return hibernateUtil.fetchById(id, getType());
    }

    @Override
    public List<T> getByName(String name) {
        String query = "SELECT " + String.join(",", getTableFields()) 
                       + " FROM " + getTableName() +" "
                       + " WHERE "+ getSearchNameField() +" like '%" + name + "%'";
        List<Object[]> objects = hibernateUtil.fetchAll(query);
        List<T> users = new ArrayList<>();
        for (Object[] object : objects) {
            users.add(populateObject(object));
        }
        return users;
    }
    
}
