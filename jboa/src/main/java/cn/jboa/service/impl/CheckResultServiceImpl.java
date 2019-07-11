package cn.jboa.service.impl;

import java.util.Date;

import cn.jboa.common.Constants;
import cn.jboa.dao.CheckResultDao;
import cn.jboa.dao.ClaimVoucherDao;
import cn.jboa.dao.EmployeeDao;
import cn.jboa.entity.CheckResult;
import cn.jboa.entity.ClaimVoucher;
import cn.jboa.entity.Employee;
import cn.jboa.service.CheckResultService;

public class CheckResultServiceImpl implements CheckResultService {
	private CheckResultDao checkResultDao;
	private ClaimVoucherDao claimVoucherDao;
	private EmployeeDao employeeDao;
	
	@Override
	public boolean saveCheckResult(CheckResult checkResult) {
		// TODO Auto-generated method stub
		boolean bRet = false;
		try{
			Long claimId = checkResult.getClaimId();
			ClaimVoucher claimVoucher = (ClaimVoucher)claimVoucherDao.get(claimId);
			Employee empCheck = checkResult.getCheckEmployee();
			claimVoucher = updateClaimVoucherStatus(empCheck.getSysPosition().getNameCn(),
					checkResult.getResult(),claimVoucher);
			claimVoucherDao.update(claimVoucher);
			//设置当前时间
			claimVoucher.setModifyTime(new Date(System.currentTimeMillis()));
			checkResult.setCheckTime(new Date(System.currentTimeMillis()));
			checkResultDao.save(checkResult);
			
			bRet = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return bRet;
	}
	
	private ClaimVoucher updateClaimVoucherStatus(String position,String checkResult,ClaimVoucher claimVoucher){
		if (checkResult.equals(Constants.CHECKRESULT_PASS)){
			//已通过
			if (position.equals(Constants.POSITION_FM)){
				if (claimVoucher.getTotalAccount() >= 5000){
					//待审批，并且处理人为总经理
					claimVoucher.setStatus(Constants.CLAIMVOUCHER_APPROVING);
					claimVoucher.setNextDeal(employeeDao.getGeneralManager());
				}else{
					//已审批，处理人为财务
					claimVoucher.setStatus(Constants.CLAIMVOUCHER_APPROVED);
					claimVoucher.setNextDeal(employeeDao.getCashier());
				}
			}else if(position.equals(Constants.POSITION_GM)){
				//财务已审批，处理人为NULL
				claimVoucher.setStatus(Constants.CLAIMVOUCHER_APPROVED);
				claimVoucher.setNextDeal(employeeDao.getCashier());
			}else{
				claimVoucher.setStatus(Constants.CLAIMVOUCHER_PAID);
				claimVoucher.setNextDeal(null);
			}
		}else if(checkResult.equals(Constants.CHECKRESULT_REJECT)){
			//已拒绝
			claimVoucher.setStatus(Constants.CLAIMVOUCHER_TERMINATED);
			claimVoucher.setNextDeal(null);
			
		}else if(checkResult.equals(Constants.CHECKRESULT_BACK)){
			//已打回
			claimVoucher.setStatus(Constants.CLAIMVOUCHER_BACK);
			claimVoucher.setNextDeal(claimVoucher.getCreator());
		}
		
		return  claimVoucher;
	}
	

	public CheckResultDao getCheckResultDao() {
		return checkResultDao;
	}

	public void setCheckResultDao(CheckResultDao checkResultDao) {
		this.checkResultDao = checkResultDao;
	}


	public ClaimVoucherDao getClaimVoucherDao() {
		return claimVoucherDao;
	}


	public void setClaimVoucherDao(ClaimVoucherDao claimVoucherDao) {
		this.claimVoucherDao = claimVoucherDao;
	}


	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}


	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	

	

}
