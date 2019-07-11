package cn.jboa.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.jboa.dao.ClaimVouYearStatisticsDao;
import cn.jboa.entity.ClaimVouyearStatistics;
import cn.jboa.service.ClaimVouyearStatisticsService;
import cn.jboa.util.PaginationSupport;

public class ClaimVouyearStatisticsServiceImpl implements ClaimVouyearStatisticsService {
	
	static Logger logger = Logger.getLogger(ClaimVouyearStatisticsServiceImpl.class);
	
	private ClaimVouYearStatisticsDao dao;
	public void setDao(ClaimVouYearStatisticsDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void saveVoucherStatisticsByYear() {
		// TODO Auto-generated method stub
		try{
			dao.saveBySchedulerSearchOfYear();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public PaginationSupport<ClaimVouyearStatistics> findDeptYearStatistics(
			int startYear, int endYear, int departmentId, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return dao.findDeptYearStatistics(startYear, endYear, departmentId, pageNo, pageSize);
	}

	@Override
	public List<ClaimVouyearStatistics> findCompYearStatistics(int startYear,
			int endYear) {
		// TODO Auto-generated method stub
		List result = dao.findCompYearStatistics(startYear, endYear);
		Iterator it = result.iterator();
		List<ClaimVouyearStatistics> newList =new ArrayList<ClaimVouyearStatistics>();
		int i = 0;
		while(it.hasNext()){
			Object[] o = (Object[])it.next();
			ClaimVouyearStatistics cvs = new ClaimVouyearStatistics();
			i++;
			cvs.setId(new Long(i));
			cvs.setTotalCount((Double)o[0]);
			cvs.setYear((Integer)o[1]);
			newList.add(cvs);
		}
		return newList;
	}
	
	
}
