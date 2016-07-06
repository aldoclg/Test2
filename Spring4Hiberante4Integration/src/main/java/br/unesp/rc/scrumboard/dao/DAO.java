package br.unesp.rc.scrumboard.dao;

import java.util.List;

/**
 * Interface base para Dao.
 * @author Vinicius
 */
public interface DAO<T> {
	
    public void create(T entity);

    public T update(T entity);

    public void delete(long id);

    public List<T> getAll();

    public T get(long id);
    
    public List<T> getByName(String name);
}
