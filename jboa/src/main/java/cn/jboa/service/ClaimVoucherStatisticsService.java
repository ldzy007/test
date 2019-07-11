package cn.jboa.service;

import java.util.List;

import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.util.PaginationSupport;

public interface ClaimVoucherStatisticsService {
	public void saveVoucherStatisticsByMonth();
	
	public PaginationSupport<ClaimVoucherStatistics> getDepClaimVoucherStatisticsByPage(int year, int startMonth, int endMonth, int departmentId,int pageNo, int pageSize);
	
	
	public List<ClaimVoucherStatistics> getDepClaimVoucherByMonth(int year,int month);
	
	public List<ClaimVoucherStatistics> getCompClaimVoucherByMonth(int year, int startMonth, int endMonth);
}
