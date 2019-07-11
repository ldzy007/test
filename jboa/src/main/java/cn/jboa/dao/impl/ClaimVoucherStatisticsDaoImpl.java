package cn.jboa.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cn.jboa.dao.ClaimVoucherStatisticsDao;
import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.entity.Department;
import cn.jboa.util.DateUtil;
import cn.jboa.util.PaginationSupport;


public class ClaimVoucherStatisticsDaoImpl extends BaseHibernateDaoSupport<ClaimVoucherStatistics> implements ClaimVoucherStatisticsDao{

	public void saveBySchedulerSearch() {
		//查询
		Date firstDayOfLastMonth = DateUtil.getFirstDayOfLastMonth();
		Date lastDayOfLastMonth = DateUtil.getLastDayOfLastMonth();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(bcv.totalAccount),bcv.creator.sysDepartment.id ");
		sql.append(" from ClaimVoucher bcv ");
		sql.append(" where bcv.modifyTime <=:lastDate " );
		sql.append(" and bcv.modifyTime >=:firstDate ");
		sql.append(" and bcv.status = '已付款' ");
		sql.append(" group by bcv.creator.sysDepartment.id ");
		
		String[] paramNames = new String[]{"lastDate","firstDate"};
		Date[] values = new Date[]{lastDayOfLastMonth,firstDayOfLastMonth};
		List list = this.getHibernateTemplate().findByNamedParam(sql.toString(), paramNames, values);
		
		//入库
		int year = DateUtil.getYear(firstDayOfLastMonth);
		int month = DateUtil.getMonth(firstDayOfLastMonth);
		
		Iterator it = list.iterator();
		while(it.hasNext()){
			ClaimVoucherStatistics result = new ClaimVoucherStatistics();
			Object[] o = (Object[])it.next();
			result.setTotalCount((Double)o[0]);
			Department dept = this.getHibernateTemplate().get(Department.class, (Integer)o[1]);
			result.setDepartment(dept);
			result.setYear(year);
			result.setMonth(month);
			result.setModifyTime(new Date());
			this.getHibernateTemplate().save(result);
		}
		System.out.print("入库完毕");
	}

	public PaginationSupport<ClaimVoucherStatistics> getDepClaimVoucherStatisticByPage(
			int year, int startMonth, int endMonth,int departmentId, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(ClaimVoucherStatistics.class);
		if(year!=0){
			criteria.add(Restrictions.eq("year", year));
		}
		if(startMonth!=0){
			criteria.add(Restrictions.ge("month", startMonth));
		}
		if(endMonth!=0){
			criteria.add(Restrictions.le("month", endMonth));
		}
		if(departmentId!=0){
			criteria.add(Restrictions.eq("department.id", departmentId));
		}
		criteria.addOrder(Order.desc("month"));
		return findPageByCriteria(criteria, pageNo, pageSize);
		
	}

	public List<ClaimVoucherStatistics> getDepClaimVoucherByMonth(int year,
			int month) {
		// TODO Auto-generated method stub
		Criteria criteria = this.getSession().createCriteria(ClaimVoucherStatistics.class);
		if(year==0 || month == 0){
			return null;
		}
		criteria.add(Restrictions.eq("year", year));
		criteria.add(Restrictions.eq("month", month));
		
		List<ClaimVoucherStatistics> result = criteria.list();
		return result;
	}

	public List getCompClaimVoucherByMonth(int year, int startMonth, int endMonth) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		List<Integer> value = new ArrayList<Integer>();
		sql.append(" select sum(cvx.totalCount), cvx.year, cvx.month ");
		sql.append(" from ClaimVoucherStatistics cvx ");
		
		if(startMonth!=0){
			sql.append(" where cvx.month>=? ");
			value.add(startMonth);
			
		}else{
			sql.append(" where cvx.month>=1 ");
		}
		if(endMonth!=0){
			sql.append(" and cvx.month<=? ");
			value.add(endMonth);
			
		}else{
			sql.append(" and cvx.month<=12 ");
		}
		if(year != 0){
			sql.append(" and cvx.year=?  ");
			value.add(year);
			
		}
		sql.append(" group by cvx.year, cvx.month ");
		sql.append(" order by cvx.month DESC ");
		
		List list;
		if(value.size()!=0){
			list = this.getHibernateTemplate().find(sql.toString(), value.toArray());
		}else{
			list = this.getHibernateTemplate().find(sql.toString());
		}
		
		
		return list;
	}
	
	
	/*@Override
	public List getDepClaimVoucherByYear(int year,int departmentId) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append(" select sum(cvx.totalCount), cvx.year, cvx.department.id ");
		sql.append(" from ClaimVoucherStatistics cvx ");
		if(year != 0){
			sql.append(" where cvx.year=:year  ");
		}
		if(departmentId != 0){
			sql.append(" and cvx.department.id=:departmentId ");
		}
		sql.append(" group by cvx.year, cvx.department.id ");
		String[] paramNames = new String[]{"year","departmentId"};
		Integer[] values = new Integer[]{year,departmentId};
		List list = this.getHibernateTemplate().findByNamedParam(sql.toString(), paramNames, values);
		
		return list;
	}*/
	
	

	

}
