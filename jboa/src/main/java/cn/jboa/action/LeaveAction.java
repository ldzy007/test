package cn.jboa.action;

import java.util.Map;

import cn.jboa.common.Constants;
import cn.jboa.entity.Employee;
import cn.jboa.entity.Leave;
import cn.jboa.service.EmployeeService;
import cn.jboa.service.LeaveService;

public class LeaveAction extends BaseAction {
	private LeaveService leaveService;
	private EmployeeService empService;
	private Leave leave;
	private String startDate;
	private String endDate;
	
	private Map leaveTypeMap;
	private Employee nextDeal;
	
	public String searchLeave() {
		String createSn = "";
		String dealSn = "";
		if (isStaff()){
			createSn = getCurrentSn();
		}else{
			dealSn = getCurrentSn();
		}
		
		pageSupport = leaveService.getLeavePage(createSn, dealSn, 
				       startDate, endDate, pageNo, pageSize);
		
		return "list";
	}
	
	public String toEdit() {
		return "edit";
	}
	public String toCheck() {
		leave = leaveService.findLeaveById(leave.getId());
		return "check";
	}
	
	public String getLeaveById() {
		leave = leaveService.findLeaveById(leave.getId());
		return "view";
	}
	public String saveLeave(){
		leaveService.saveLeave(leave);
		return "redirectList";
	}
	
	public String checkLeave() {
		leaveService.checkLeave(leave);
		return "redirectList";
	}

	public LeaveService getLeaveService() {
		return leaveService;
	}

	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map getLeaveTypeMap() {
		return leaveService.getLeaveTypeMap();
	}

	public void setLeaveTypeMap(Map leaveTypeMap) {
		this.leaveTypeMap = leaveTypeMap;
	}

	public Employee getNextDeal() {
		return nextDeal;
	}

	public void setNextDeal(Employee nextDeal) {
		this.nextDeal = nextDeal;
	}

	public EmployeeService getEmpService() {
		return empService;
	}

	public void setEmpService(EmployeeService empService) {
		this.empService = empService;
	}
	
	
	

}
