package cn.jboa.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import cn.jboa.util.PaginationSupport;



public interface BaseDao<T> {
	
	/**
	 * Save or update the instance
	 * @param domain Domain to saveOrUpdate
	 */
	public void saveOrUpdate(T instance);
	
	/**
	 * delete the domain from database
	 * @param domain domain to remove
	 * @author <a href="">mail</a>
	 */
	public void delete(T instance);

	/**
	 * @param id
	 * @return
	 */
	public T get(Serializable id);
	
	public T save(T instance)throws DataAccessException;
	public T update(T instance)throws DataAccessException;
	
	public List<T> query(DetachedCriteria criteria,int firstResult,int maxResults) throws DataAccessException;
	
	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int pageNo, final int pageSize);
	
	public List find(String hql) throws DataAccessException;
	public List find(String hql,Object value) throws DataAccessException;
	public List find(String hql,Object... values) throws DataAccessException;
	public Object findFirst(String hql) throws DataAccessException;
	public Object findFirst(String hql,Object value) throws DataAccessException;
	public Object findFirst(String hql,Object... values) throws DataAccessException;
}
