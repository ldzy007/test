package cn.jboa.service;

import java.util.List;

import cn.jboa.entity.ClaimVouyearStatistics;
import cn.jboa.util.PaginationSupport;

public interface ClaimVouyearStatisticsService {
	public void saveVoucherStatisticsByYear();
	
	public PaginationSupport<ClaimVouyearStatistics> findDeptYearStatistics(int startYear, int endYear, int departmentId, int pageNo, int pageSize);
	
	public List<ClaimVouyearStatistics> findCompYearStatistics(int startYear, int endYear);
}
