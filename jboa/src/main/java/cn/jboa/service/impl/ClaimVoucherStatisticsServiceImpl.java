package cn.jboa.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.jboa.dao.ClaimVoucherStatisticsDao;
import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.service.ClaimVoucherStatisticsService;
import cn.jboa.util.PaginationSupport;

public class ClaimVoucherStatisticsServiceImpl implements ClaimVoucherStatisticsService{
	static Logger logger = Logger.getLogger(ClaimVoucherStatisticsServiceImpl.class);
	
	private ClaimVoucherStatisticsDao statisticsDao;
	public void setStatisticsDao(ClaimVoucherStatisticsDao statisticsDao) {
		this.statisticsDao = statisticsDao;
	}
	
	@Override
	public void saveVoucherStatisticsByMonth() {
		try{
			statisticsDao.saveBySchedulerSearch();
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		
	}
	
	@Override
	public PaginationSupport<ClaimVoucherStatistics> getDepClaimVoucherStatisticsByPage(
			int year, int startMonth, int endMonth,int departmentId, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		PaginationSupport<ClaimVoucherStatistics> list = statisticsDao.getDepClaimVoucherStatisticByPage(year,startMonth,endMonth,departmentId,pageNo,pageSize);
		return list;
	}

	@Override
	public List<ClaimVoucherStatistics> getDepClaimVoucherByMonth(int year,
			int month) {
		// TODO Auto-generated method stub
		List<ClaimVoucherStatistics> list = statisticsDao.getDepClaimVoucherByMonth(year, month);
		return list;
	}

	@Override
	public List<ClaimVoucherStatistics> getCompClaimVoucherByMonth(int year, int startMonth, int endMonth) {
		// TODO Auto-generated method stub
		List result = statisticsDao.getCompClaimVoucherByMonth(year, startMonth, endMonth);
		Iterator it = result.iterator();
		List<ClaimVoucherStatistics> newList = new ArrayList<ClaimVoucherStatistics>();
		int i=0;
		while(it.hasNext()){
			Object[] o = (Object[])it.next();
			ClaimVoucherStatistics cvs = new ClaimVoucherStatistics();
			i++;
			cvs.setId(new Long(i));
			cvs.setTotalCount((Double)o[0]);
			cvs.setYear((Integer)o[1]);
			cvs.setMonth((Integer)o[2]);
			newList.add(cvs);
		}
		return newList;
	}
	
	
	
}
