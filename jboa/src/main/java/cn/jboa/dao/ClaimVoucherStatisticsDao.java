package cn.jboa.dao;

import java.util.List;

import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.util.PaginationSupport;

public interface ClaimVoucherStatisticsDao extends BaseDao<ClaimVoucherStatistics> {
	public void saveBySchedulerSearch();
	public PaginationSupport<ClaimVoucherStatistics> getDepClaimVoucherStatisticByPage(
			            int year, int startMonth, int endMonth, int departmentId,int pageNo,int pageSize);
	
	//查询部门的报销汇总信息
	public List<ClaimVoucherStatistics> getDepClaimVoucherByMonth(int year, int month);
	
	//聚合部年度的报销汇总信息
//	public List getDepClaimVoucherByYear(int year, int departmentId);
	
	//按照年和月份，聚合全公司的报销汇总信息
	public List getCompClaimVoucherByMonth(int year, int startMonth, int endMonth);
	
	
}
