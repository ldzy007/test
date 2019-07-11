package cn.jboa.dao;

import java.util.List;

import cn.jboa.entity.ClaimVouyearStatistics;
import cn.jboa.util.PaginationSupport;

public interface ClaimVouYearStatisticsDao {
	public void saveBySchedulerSearchOfYear();
	
	public PaginationSupport<ClaimVouyearStatistics> findDeptYearStatistics(int startYear, int endYear, int departmentId,int pageNo, int pageSize);
	
	public List findCompYearStatistics(int startYear, int endYear);
}
