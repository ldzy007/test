package cn.jboa.dao;

import cn.jboa.entity.Leave;
import cn.jboa.util.PaginationSupport;

public interface LeaveDao extends BaseDao<Leave> {
	
	public PaginationSupport<Leave> getLeavePage(String createSn,String dealSn,
			String startDate,String endDate,int pageNo,int pageSize);

}
