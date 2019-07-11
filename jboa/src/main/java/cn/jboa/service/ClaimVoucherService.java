package cn.jboa.service;

import java.util.List;
import java.util.Map;

import cn.jboa.entity.ClaimVoucher;
import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.util.PaginationSupport;

public interface ClaimVoucherService {
	
	public Map getAllStatusMap();
	
	public PaginationSupport<ClaimVoucher> getClaimVoucherPage(String createSn,String dealSn,
			String status,String startDate,String endDate,int pageNo,int pageSize);
	
	public ClaimVoucher findClaimVoucherById(Long id);

	
	public List<ClaimVoucher> getClaimVoucherByModifyDate(int year, int month, int departmentId);
	
	public List<ClaimVoucherStatistics> getClaimVoucherByDateAndDept(int year, int month);
	
	public boolean deleteClaimVoucherById(Long id);
	
	public boolean saveClaimVoucher(ClaimVoucher claimVoucher);
	public boolean updateClaimVoucher(ClaimVoucher claimVoucher);


}
