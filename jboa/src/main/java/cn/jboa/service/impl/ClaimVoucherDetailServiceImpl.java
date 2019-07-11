package cn.jboa.service.impl;

import cn.jboa.dao.ClaimVoucherDetailDao;
import cn.jboa.service.ClaimVoucherDetailService;

public class ClaimVoucherDetailServiceImpl implements ClaimVoucherDetailService {

	private ClaimVoucherDetailDao claimVoucherDetailDao;

	public ClaimVoucherDetailDao getClaimVoucherDetailDao() {
		return claimVoucherDetailDao;
	}

	public void setClaimVoucherDetailDao(ClaimVoucherDetailDao claimVoucherDetailDao) {
		this.claimVoucherDetailDao = claimVoucherDetailDao;
	}

	
	

}
