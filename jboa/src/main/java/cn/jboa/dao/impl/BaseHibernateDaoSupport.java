package cn.jboa.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl.OrderEntry;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.jboa.dao.BaseDao;
import cn.jboa.util.HibernateUtils;
import cn.jboa.util.PaginationSupport;

public abstract class BaseHibernateDaoSupport<T> extends HibernateDaoSupport implements BaseDao<T> {

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseHibernateDaoSupport() {
		entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void saveOrUpdate(T instance) {
		getHibernateTemplate().saveOrUpdate(instance);
	}

	/* (non-Javadoc)
	 * @see com.pb.blog.dao.BaseDao#delete(java.lang.Object)
	 */
	public void delete(T instance) {
		getHibernateTemplate().delete(instance);
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		T o = (T) getHibernateTemplate().get(entityClass, id);
		return o;
	}
	public T save(T instance) throws DataAccessException {
		getHibernateTemplate().save(instance);
		return instance;
	}
	public T update(T instance) throws DataAccessException {
		getHibernateTemplate().update(instance);
		return instance;
	}
	public List<T> query(DetachedCriteria criteria,int firstResult,int maxResults) throws DataAccessException {
		
		return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResults);
	}
	
	public PaginationSupport<T> findPageByCriteria(
			final DetachedCriteria detachedCriteria, final int pageNo, final int pageSize){
		return (PaginationSupport<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria executableCriteria = detachedCriteria.getExecutableCriteria(session);

				// Get the orginal orderEntries
				OrderEntry[] orderEntries = HibernateUtils.getOrders(executableCriteria);
				// Remove the orders
				executableCriteria = HibernateUtils.removeOrders(executableCriteria);
				// get the original projection
				Projection projection = HibernateUtils.getProjection(executableCriteria);

				int totalCount = ((Integer) executableCriteria.setProjection(Projections.rowCount()).uniqueResult())
						.intValue();

				executableCriteria.setProjection(projection);
				if (projection == null) {
					// Set the ResultTransformer to get the same object
					// structure with hql
					executableCriteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
				}
				// Add the orginal orderEntries
				executableCriteria = HibernateUtils.addOrders(executableCriteria, orderEntries);

				// Now, the Projection and the orderEntries have been resumed
				PaginationSupport pageSupport = new PaginationSupport(pageNo,pageSize);
				int firstResult = pageSupport.getStartRow();
				List<T> items = HibernateUtils.getPageResult(executableCriteria, firstResult, pageSize);
				pageSupport.setItems(items);
				pageSupport.setTotalCount(totalCount);
				return  pageSupport;
			}
		});
	}
	
	public List find(String hql) throws DataAccessException {
		return getHibernateTemplate().find(hql);
	}
	
	public List find(String hql, Object value) throws DataAccessException {
		return getHibernateTemplate().find(hql,value);
	}
	
	public List find(String hql, Object... values) throws DataAccessException {
		return getHibernateTemplate().find(hql,values);
	}
	
	public Object findFirst(String hql) throws DataAccessException {
		List list = find(hql);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	public Object findFirst(String hql, Object value)
			throws DataAccessException {
		List list = find(hql,value);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	public Object findFirst(String hql, Object... values)
			throws DataAccessException {
		List list = find(hql,values);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
}
