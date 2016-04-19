package br.org.venturus.tambor.dao.impl;


import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.ejb.EntityManagerImpl;

import br.org.venturus.tambor.utils.dao.GenericDao;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
	
	private Class<T> persistentClass;
	private EntityManager em;
	private Session session;
	
	
	@SuppressWarnings("unchecked")
	public GenericDaoImpl(EntityManager em) {	
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.em = em;
	}//fim do método
	
	
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public void insert(T obj) {	
		
		try {			
			getEm().getTransaction().begin();
			getEm().persist(obj);
			getEm().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			getEm().getTransaction().rollback();
		}		
	}//fim do método

	public void merge(T obj) {
		try {			
			getEm().getTransaction().begin();			
			getEm().merge(obj);
			getEm().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			getEm().getTransaction().rollback();
		}	
	}//fim do método

	public void remove(T obj) {
		
		try {
			getEm().getTransaction().begin();
			getEm().remove(obj);
			getEm().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			getEm().getTransaction().rollback();
		}	
		
	}//fim do método

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String... excludeProperty) {
		
		Session session = (Session) getEm();
		Criteria criteria = session.createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		
		return criteria.list();
	}

	public List<T> findAll() {		
		return findByCriteria();
	}

	public T findById(int id, boolean lock) {
		
		if (lock)
			return (T) getEm().find(persistentClass, id, LockModeType.READ);
		else
			return (T) getEm().find(persistentClass, id);	
		
	}//fim do método

	public List<T> findByExample(T obj) {
		return findByExample(obj, new String[0]);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByHQL(String hql) {		
		return getEm().createQuery(hql).getResultList();
	}//fim do método	
	
	protected List<T> findByCriteria(final Criterion... criterion) {
		return findByCriteria(-1, -1, null, criterion);
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final int firstResult, final int maxResults, final Order order, final Criterion... criterion) {		
		
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		for(Criterion c : criterion) {
			if (c != null)
				criteria.add(c);
		}//fom do for
		
		if (firstResult > 0) {
			criteria.addOrder(order);
		}
		
		if (maxResults > 0) {
			criteria.setMaxResults(maxResults);
		}	
		
		return criteria.list();		
		
	}//fim do método
	
	public void flush() {
		em.flush();
	}

	public void clear() {
		em.clear();		
	}

	public EntityManager getEm() {
		return em;
	}	

	public Session getSession() {
		if (em != null) this.session = (Session) ((EntityManagerImpl) getEm()).getDelegate();
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
