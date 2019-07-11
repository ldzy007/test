package cn.jboa.dao.impl;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.jboa.dao.LeaveDao;
import cn.jboa.entity.ClaimVoucher;
import cn.jboa.entity.Leave;
import cn.jboa.util.DateUtil;
import cn.jboa.util.PaginationSupport;

public class LeaveDaoImpl extends BaseHibernateDaoSupport<Leave> implements
		LeaveDao {

	public PaginationSupport<Leave> getLeavePage(String createSn,
			String dealSn, String startDate, String endDate, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(Leave.class);
		if (createSn != null && !createSn.equals("")){
			criteria.add(Restrictions.eq("creator.sn", createSn));
		}
		if (dealSn != null && !dealSn.equals("")){
			criteria.add(Restrictions.eq("nextDeal.sn", dealSn));
		}
		
		Date dStartDate = null;
		Date dEndDate = null;
		try {
			if(startDate != null && !startDate.equals("")){
				dStartDate = DateUtil.strToDate(startDate,"yyyy-MM-dd");
				criteria.add(Restrictions.ge("createTime", dStartDate));
			}
			if(endDate != null && !endDate.equals("")){
				dEndDate = DateUtil.strToDate(endDate+" 23:59:59","yyyy-MM-dd hh:mm:ss");
				criteria.add(Restrictions.le("createTime", dEndDate));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return findPageByCriteria(criteria, pageNo, pageSize);
	}

}
