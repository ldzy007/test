package cn.jboa.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.jboa.common.Constants;
import cn.jboa.dao.EmployeeDao;
import cn.jboa.dao.LeaveDao;
import cn.jboa.entity.Leave;
import cn.jboa.service.LeaveService;
import cn.jboa.service.MailService;
import cn.jboa.util.PaginationSupport;

public class LeaveServiceImpl implements LeaveService {
	private LeaveDao leaveDao;
	private EmployeeDao empDao; 
	private MailService mailService;
	@Override
	public Map getLeaveTypeMap() {
		// TODO Auto-generated method stub
		Map leaveMap = new LinkedHashMap();
		leaveMap.put(Constants.LEAVE_ANNUAL, Constants.LEAVE_ANNUAL);
		leaveMap.put(Constants.LEAVE_CASUAL, Constants.LEAVE_CASUAL);
		leaveMap.put(Constants.LEAVE_MARRIAGE, Constants.LEAVE_MARRIAGE);
		leaveMap.put(Constants.LEAVE_MATERNITY, Constants.LEAVE_MATERNITY);
		leaveMap.put(Constants.LEAVE_SICK, Constants.LEAVE_SICK);
		return leaveMap;
	}

	@Override
	public PaginationSupport<Leave> getLeavePage(String createSn,
			String dealSn, String startDate, String endDate, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return leaveDao.getLeavePage(createSn, dealSn, startDate, endDate, pageNo, pageSize);
	}

	@Override
	public Leave findLeaveById(Long id) {
		// TODO Auto-generated method stub
		return (Leave)leaveDao.get(id);
	}
	
	@Override
	public boolean saveLeave(Leave leave) {
		// TODO Auto-generated method stub
		boolean bRet = false;
		try{
			leave.setStatus(Constants.LEAVESTATUS_APPROVING);
			leave.setCreateTime(new Date());
			leaveDao.save(leave);
			//send mail
			String recevier =empDao.findEmployeeBySn(leave.getNextDeal().getSn()).getName();
			String leaveApplier = empDao.findEmployeeBySn(leave.getCreator().getSn()).getName();
			String leaveDays = leave.getLeaveDay().toString();
			Map<String,String> tempData = new HashMap<String,String>();
			tempData.put("recevier", recevier);
			tempData.put("leaveApplier", leaveApplier);
			tempData.put("leaveDays", leaveDays);
			String fromMail = "tina@mail.com";
			String toMail = "jerry@mail.com";
			mailService.sendMail(fromMail,toMail,"请假流程审批进度提醒邮件", tempData);
			
			bRet = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return bRet;
	}
	public boolean checkLeave(Leave leave) {
		// TODO Auto-generated method stub
		boolean bRet = false;
		try{
			Leave oldLeave = leaveDao.get(leave.getId());
			oldLeave.setStatus(leave.getStatus());
			oldLeave.setApproveOpinion(leave.getApproveOpinion());
			oldLeave.setModifyTime(new Date());
			leaveDao.save(oldLeave);
			bRet = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return bRet;
	}
	
	
	public LeaveDao getLeaveDao() {
		return leaveDao;
	}

	public void setLeaveDao(LeaveDao leaveDao) {
		this.leaveDao = leaveDao;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}

	


}
