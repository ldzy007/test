package cn.jboa.action;

import cn.jboa.entity.CheckResult;
import cn.jboa.service.CheckResultService;

public class CheckResultAction extends BaseAction {
	private CheckResultService checkResultService;
	private CheckResult checkResult;
	
	
	
	public String checkClaimVoucher(){
		checkResult.setCheckEmployee(getLoginEmployee());
		boolean bRet = checkResultService.saveCheckResult(checkResult);
		if (bRet){
			return SUCCESS;
		}else{
			return INPUT;
		}
	}

	public CheckResultService getCheckResultService() {
		return checkResultService;
	}

	public void setCheckResultService(CheckResultService checkResultService) {
		this.checkResultService = checkResultService;
	}

	public CheckResult getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(CheckResult checkResult) {
		this.checkResult = checkResult;
	}
	
	

}
