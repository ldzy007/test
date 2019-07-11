package cn.jboa.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cn.jboa.dao.ClaimVouYearStatisticsDao;
import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.entity.ClaimVouyearStatistics;
import cn.jboa.entity.Department;
import cn.jboa.util.DateUtil;
import cn.jboa.util.PaginationSupport;

public class ClaimVouYearStatisticsDaoImpl extends BaseHibernateDaoSupport<ClaimVouyearStatistics> 
		implements ClaimVouYearStatisticsDao{

	public void saveBySchedulerSearchOfYear() {
		// TODO Auto-generated method stub
		//查询
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(cvs.totalCount), cvs.year, cvs.department.id ");
		sql.append(" from ClaimVoucherStatistics cvs ");
		sql.append(" where cvs.year =:year ");
		sql.append(" group by cvs.department.id, cvs.year ");
		
		String[] paramNames = new String[]{"year"};
//		Integer[] values = new Integer[]{DateUtil.getLastYear()};
		Integer[] values = new Integer[]{2013};
		List list = this.getHibernateTemplate().findByNamedParam(sql.toString(), paramNames, values);
		
		//入库
		Iterator it = list.iterator();
		while(it.hasNext()){
			ClaimVouyearStatistics result = new ClaimVouyearStatistics();
			Object[] o = (Object[])it.next();
			result.setTotalCount((Double)o[0]);
			result.setYear((Integer)o[1]);
			Department dept = this.getHibernateTemplate().get(Department.class, (Integer)o[2]);
			result.setDept(dept);
			result.setModifyTime(new Date());
			this.getHibernateTemplate().save(result);
		}
		System.out.print("入库完毕");
		
	}

	public PaginationSupport<ClaimVouyearStatistics> findDeptYearStatistics(int startYear, int endYear, int departmentId, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria =DetachedCriteria.forClass(ClaimVouyearStatistics.class);
		if(startYear!=0){
			criteria.add(Restrictions.ge("year", startYear));
		}
		if(endYear!=0){
			criteria.add(Restrictions.le("year", endYear));
		}
		if(departmentId!=0){
			criteria.add(Restrictions.eq("dept.id", departmentId));
		}
		criteria.addOrder(Order.desc("year"));
		return findPageByCriteria(criteria, pageNo, pageSize);
	}

	public List findCompYearStatistics(int startYear,
			int endYear) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		List<Integer> value = new ArrayList<Integer>();
		sql.append(" select sum(cvs.totalCount), cvs.year ");
		sql.append(" from ClaimVouyearStatistics cvs ");
		if(startYear!=0 && endYear!=0){
			sql.append(" where cvs.year>=? and cvs.year<=? ");
			value.add(startYear);
			value.add(endYear);
		}else{
			if(startYear!=0){
				sql.append(" where cvs.year>=:startYear ");
				value.add(startYear);
			}
			if(endYear!=0){
				sql.append(" where cvs.year<=:endYear ");
				value.add(endYear);
			}
		}
		sql.append(" group by cvs.year ");
		sql.append(" order by cvs.year DESC ");
		
		List result;
		if(value.size()!=0){
			result = this.getHibernateTemplate().find(sql.toString(), value.toArray());
		}else{
			result = this.getHibernateTemplate().find(sql.toString());
		}
		
		return result;
	}
	
	
	
	

}
