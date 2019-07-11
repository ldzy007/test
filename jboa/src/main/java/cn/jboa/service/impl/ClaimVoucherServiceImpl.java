package cn.jboa.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.jboa.common.Constants;
import cn.jboa.dao.ClaimVoucherDao;

import cn.jboa.dao.EmployeeDao;

import cn.jboa.dao.ClaimVoucherDetailDao;

import cn.jboa.entity.ClaimVoucher;

import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.entity.Department;
import cn.jboa.entity.Employee;

import cn.jboa.entity.ClaimVoucherDetail;

import cn.jboa.service.ClaimVoucherService;
import cn.jboa.util.PaginationSupport;

public class ClaimVoucherServiceImpl implements ClaimVoucherService {
	
	private ClaimVoucherDao claimVoucherDao;

	private EmployeeDao employeeDao;

	private ClaimVoucherDetailDao claimVoucherDetailDao;


	@Override
	public PaginationSupport<ClaimVoucher> getClaimVoucherPage(String createSn,
			String dealSn, String status, String startDate, String endDate, int pageNo,
			int pageSize) {
		// TODO Auto-generated method stub
		return claimVoucherDao.getClaimVoucherPage(createSn, dealSn, status, startDate, endDate, pageNo, pageSize);
	}
	

	@Override
	public boolean saveClaimVoucher(ClaimVoucher claimVoucher) {
		// TODO Auto-generated method stub
		boolean bRet = false;
		try{
			claimVoucher.setCreateTime(new Date());
			claimVoucherDao.save(claimVoucher);
			for(ClaimVoucherDetail detail:claimVoucher.getDetailList()){
				detail.setBizClaimVoucher(claimVoucher);
				claimVoucherDetailDao.save(detail);
			}
			bRet = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return bRet;
	}
	@Override
	public boolean updateClaimVoucher(ClaimVoucher claimVoucher) {
		// TODO Auto-generated method stub
		boolean bRet = false;
		try{
			claimVoucher.setModifyTime(new Date());
			claimVoucherDao.update(claimVoucher);
			if (claimVoucher.getDetailList() != null){
				for(ClaimVoucherDetail detail:claimVoucher.getDetailList()){
					detail.setBizClaimVoucher(claimVoucher);
					System.out.println("id==="+detail.getId());
					claimVoucherDetailDao.saveOrUpdate(detail);
				}
			}
			bRet = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return bRet;
	}
	
	@Override
	public Map getAllStatusMap() {
		// TODO Auto-generated method stub
		Map statusMap = new LinkedHashMap();
		statusMap.put(Constants.CLAIMVOUCHER_CREATED, Constants.CLAIMVOUCHER_CREATED);
		statusMap.put(Constants.CLAIMVOUCHER_SUBMITTED, Constants.CLAIMVOUCHER_SUBMITTED);
		statusMap.put(Constants.CLAIMVOUCHER_BACK, Constants.CLAIMVOUCHER_BACK);
		statusMap.put(Constants.CLAIMVOUCHER_APPROVING, Constants.CLAIMVOUCHER_APPROVING);
		statusMap.put(Constants.CLAIMVOUCHER_APPROVED, Constants.CLAIMVOUCHER_APPROVED);
		statusMap.put(Constants.CLAIMVOUCHER_PAID, Constants.CLAIMVOUCHER_PAID);
		statusMap.put(Constants.CLAIMVOUCHER_TERMINATED, Constants.CLAIMVOUCHER_TERMINATED);
		return statusMap;
	}
	
	@Override
	public ClaimVoucher findClaimVoucherById(Long id) {
		// TODO Auto-generated method stub
		return (ClaimVoucher)claimVoucherDao.get(id);
	}
	public boolean deleteClaimVoucherById(Long id) {
		// TODO Auto-generated method stub
		boolean bRet = false;
		try{
			ClaimVoucher claimVoucher = (ClaimVoucher)claimVoucherDao.get(id);
			claimVoucherDao.delete(claimVoucher);
			bRet = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return bRet;
	}
	
	public ClaimVoucherDao getClaimVoucherDao() {
		return claimVoucherDao;
	}

	public void setClaimVoucherDao(ClaimVoucherDao claimVoucherDao) {
		this.claimVoucherDao = claimVoucherDao;
	}



	@Override
	public List<ClaimVoucher> getClaimVoucherByModifyDate(int year, int month,
			int departmentId) {
		// TODO Auto-generated method stub
		List list = claimVoucherDao.getClaimVoucherByModifyDate(year, month, departmentId);
		Iterator it = list.iterator();
		List<ClaimVoucher> newList = new ArrayList<ClaimVoucher>();
		while(it.hasNext()){
			Object[] o = (Object[])it.next();
			ClaimVoucher cv = new ClaimVoucher();
			Employee emp = employeeDao.findEmployeeBySn((String)o[0]);
			cv.setCreator(emp);
			cv.setTotalAccount((Double)o[2]);
			newList.add(cv);
		}
		return newList;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}


	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}


	@Override
	public List<ClaimVoucherStatistics> getClaimVoucherByDateAndDept(int year, int month) {
		// TODO Auto-generated method stub
		List list = claimVoucherDao.getClaimVoucherByDateAndDept(year, month);
		Iterator it = list.iterator();
		List<ClaimVoucherStatistics> newList = new ArrayList<ClaimVoucherStatistics>();
		while(it.hasNext()){
			Object[] o = (Object[])it.next();
			ClaimVoucherStatistics cvs = new ClaimVoucherStatistics();
			Department dept = new Department();
			dept.setId((Integer)o[0]);
			dept.setName((String)o[1]);
			cvs.setDepartment(dept);
			cvs.setTotalCount( (Double)o[2]);
			cvs.setYear(year);
			cvs.setMonth(month);
			newList.add(cvs);
		}
		return newList;
	}


	
	
	

	public ClaimVoucherDetailDao getClaimVoucherDetailDao() {
		return claimVoucherDetailDao;
	}


	public void setClaimVoucherDetailDao(ClaimVoucherDetailDao claimVoucherDetailDao) {
		this.claimVoucherDetailDao = claimVoucherDetailDao;
	}
	





}
