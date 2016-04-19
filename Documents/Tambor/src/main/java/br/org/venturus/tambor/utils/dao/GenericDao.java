package br.org.venturus.tambor.utils.dao;

import java.util.Collection;
import java.util.List;

public interface GenericDao<T> {

	void insert(T obj);

	void merge(T obj);

	void remove(T obj);

	List<T> findByExample(T exampleInstance, String... excludeProperty);
	
	List<T> findAll();

	T findById(int id, boolean lock);

	Collection<T> findByExample(T obj);
	
	List<T> findByHQL(String hql);
	
	void flush();
	
	void clear();
}