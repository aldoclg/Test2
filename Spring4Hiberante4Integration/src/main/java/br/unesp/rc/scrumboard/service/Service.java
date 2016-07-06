package br.unesp.rc.scrumboard.service;

import java.util.List;

/**
 * Interface para um serviço
 * @author Vinicius
 */
public interface Service<T> {
	
    public void create (T entity);

    public T update(T entity);

    public void delete(long id);

    public List<T> getAll();

    public T get(long id);
    
    public List<T> getByName(String name);
}
