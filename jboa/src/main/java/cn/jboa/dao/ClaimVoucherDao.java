package cn.jboa.dao;

import java.util.List;

import cn.jboa.entity.ClaimVoucher;
import cn.jboa.util.PaginationSupport;

public interface ClaimVoucherDao extends BaseDao<ClaimVoucher> {
	
	public PaginationSupport<ClaimVoucher> getClaimVoucherPage(String createSn,String dealSn,
			String status,String startDate,String endDate,int pageNo,int pageSize);
	
	//获取一段月份期间、某部门的报销明细
	public List getClaimVoucherByModifyDate(int year, int month, int departmentId);
	
	public List getClaimVoucherByDateAndDept(int year, int month);
	
 }
