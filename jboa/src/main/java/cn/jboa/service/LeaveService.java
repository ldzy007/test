package cn.jboa.service;

import java.util.Map;

import cn.jboa.entity.Leave;
import cn.jboa.util.PaginationSupport;

public interface LeaveService {
	
	public Map getLeaveTypeMap();
	
	public PaginationSupport<Leave> getLeavePage(String createSn,String dealSn,
			String startDate,String endDate,int pageNo,int pageSize);
	
	public Leave findLeaveById(Long id);
	
	public boolean saveLeave(Leave leave);
	public boolean checkLeave(Leave leave);

}
