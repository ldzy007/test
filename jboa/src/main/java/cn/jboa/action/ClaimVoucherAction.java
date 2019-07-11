package cn.jboa.action;

import java.util.List;
import java.util.Map;

import cn.jboa.common.Constants;
import cn.jboa.entity.ClaimVoucher;
import cn.jboa.entity.ClaimVoucherDetail;
import cn.jboa.entity.Employee;
import cn.jboa.service.ClaimVoucherService;

public class ClaimVoucherAction extends BaseAction {
	
	private ClaimVoucherService claimVoucherService;
	private ClaimVoucher claimVoucher;
	private ClaimVoucherDetail claimVoucherDetail;
	private List<ClaimVoucherDetail> detailList;
	
	
	private String startDate;
	private String endDate;
	
	private String checkResult;
	
	private Map statusMap;
	
	private int rowNumber;
	
	public String searchClaimVoucher() {
		String createSn = "";
		String dealSn = "";
		if (isStaff()){
			createSn = getCurrentSn();
		}else{
			dealSn = getCurrentSn();
		}
		String status = "";
		if (claimVoucher != null){
			status = claimVoucher.getStatus();
		}
		pageSupport = claimVoucherService.getClaimVoucherPage(createSn, dealSn, 
				status, startDate, endDate, pageNo, pageSize);
		
		return "list";
	}
	
	public String getClaimVoucherById() {
		claimVoucher = claimVoucherService.findClaimVoucherById(claimVoucher.getId());
		return "view";
	}
	
	public String deleteClaimVoucherById(){
		claimVoucherService.deleteClaimVoucherById(claimVoucher.getId());
		return "redirectList";
	}
	
	public String toCheck() {
		claimVoucher = claimVoucherService.findClaimVoucherById(claimVoucher.getId());
		return "check";
	}
	
	public String toAdd() {
		return "add";
	}
	public String saveClaimVoucher(){
		claimVoucher.setCreator(getLoginEmployee());
		if (claimVoucher.getStatus().equals(Constants.CLAIMVOUCHER_SUBMITTED)){
			//状态是已提交,下一处理人是经理
			claimVoucher.setNextDeal((Employee)getSession().get(Constants.AUTH_EMPLOYEE_MANAGER));
		}
		claimVoucher.setDetailList(detailList);
		boolean bSave = claimVoucherService.saveClaimVoucher(claimVoucher);
		if(bSave){
			this.addActionMessage("添加报销单成功！");
		}else{
			this.addActionMessage("添加报销单失败！");
		}
		
		return "redirectList";
	}
	
	public String toUpdate() {
		
		claimVoucher = claimVoucherService.findClaimVoucherById(claimVoucher.getId());
		return "update";
	}
	
	public String updateClaimVoucher(){
		ClaimVoucher newClaimVoucher = claimVoucherService.findClaimVoucherById(claimVoucher.getId());
		newClaimVoucher.setCreator(getLoginEmployee());
		if (claimVoucher.getStatus().equals(Constants.CLAIMVOUCHER_SUBMITTED)){
			//状态是已提交,下一处理人是经理
			newClaimVoucher.setNextDeal((Employee)getSession().get(Constants.AUTH_EMPLOYEE_MANAGER));
		}
		newClaimVoucher.setEvent(claimVoucher.getEvent());
		newClaimVoucher.setStatus(claimVoucher.getStatus());
		newClaimVoucher.setTotalAccount(claimVoucher.getTotalAccount());
		newClaimVoucher.setDetailList(detailList);
		boolean bSave = claimVoucherService.updateClaimVoucher(newClaimVoucher);
		if(bSave){
			this.addActionMessage("添加报销单成功！");
		}else{
			this.addActionMessage("添加报销单失败！");
		}
		
		return "redirectList";
	}
	
	public Map getStatusMap() {
		return claimVoucherService.getAllStatusMap();
	}
	public ClaimVoucher getClaimVoucher() {
		return claimVoucher;
	}
	public void setClaimVoucher(ClaimVoucher claimVoucher) {
		this.claimVoucher = claimVoucher;
	}
	
	public ClaimVoucherDetail getClaimVoucherDetail() {
		return claimVoucherDetail;
	}

	public void setClaimVoucherDetail(ClaimVoucherDetail claimVoucherDetail) {
		this.claimVoucherDetail = claimVoucherDetail;
	}

	public ClaimVoucherService getClaimVoucherService() {
		return claimVoucherService;
	}
	public void setClaimVoucherService(ClaimVoucherService claimVoucherService) {
		this.claimVoucherService = claimVoucherService;
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

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public List<ClaimVoucherDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ClaimVoucherDetail> detailList) {
		this.detailList = detailList;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

}
