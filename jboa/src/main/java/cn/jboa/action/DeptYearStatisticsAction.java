package cn.jboa.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import cn.jboa.common.Constants;
import cn.jboa.entity.ClaimVoucher;
import cn.jboa.entity.Employee;
import cn.jboa.service.ClaimVoucherService;
import cn.jboa.service.ClaimVouyearStatisticsService;
import cn.jboa.util.ExportExcelUtil;
import cn.jboa.util.JFreeChatUtil;

public class DeptYearStatisticsAction extends BaseAction {
	private ClaimVouyearStatisticsService deptYearStatisticsService;
	private ClaimVoucherService claimVoucherService;
	
	private int startYear;
	private int endYear;
	private int departmentId;
	private int currYear;
	private List<ClaimVoucher> deptDetailList;
	private String detailCount;
	private String deptName;
	private JFreeChart chart;
	
	public String findDeptYearStatisticsList(){
		Employee emp = (Employee)getSession().get(Constants.AUTH_EMPLOYEE);
		departmentId = emp.getSysDepartment().getId();
		pageSupport = deptYearStatisticsService.findDeptYearStatistics(startYear, endYear, departmentId, pageNo, pageSize);
		return "list";
	}
	
	public String findDeptYearStatisticsDetail(){
		deptDetailList = claimVoucherService.getClaimVoucherByModifyDate(currYear, 0, departmentId);
		Double count = 0.0;
		for(ClaimVoucher cv:deptDetailList){
			count+=cv.getTotalAccount();
		}
		DecimalFormat df = new DecimalFormat("0.00");
		detailCount = df.format(count);
		return "detail";
	}
	
	public String createDetailExcel(){
		deptDetailList = claimVoucherService.getClaimVoucherByModifyDate(currYear, 0, departmentId);
		
		List<String[]> list = new ArrayList<String[]>();
		int i = 0;
		for(ClaimVoucher cv:deptDetailList){
			i++;
			String index = new Integer(i).toString();
			String nameCell = cv.getCreator().getName();
			String totalCell = cv.getTotalAccount().toString();
			String yearCell = new Integer(currYear).toString();
			String deptNameCell = cv.getCreator().getSysDepartment().getName();
			deptName = deptNameCell;
			list.add(new String[]{index,nameCell,totalCell,yearCell,deptNameCell});
		}
		String fileName=currYear+"年"+deptName+"年度报销统计";
		try{ 
		    ExportExcelUtil.createExcel(response, list, fileName,null,"dept");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "detailExcel";
	}
	
	public String createDetailChart(){
		deptDetailList = claimVoucherService.getClaimVoucherByModifyDate(currYear, 0, departmentId);
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		for(ClaimVoucher cv:deptDetailList){
			dataset.setValue(cv.getCreator().getName(), cv.getTotalAccount());
			deptName = cv.getCreator().getSysDepartment().getName();
		}
		String title =currYear+"年"+deptName+"年度报销统计饼图";
		chart = new JFreeChatUtil().createPieChar(dataset,title);
		
		
		return "detailChart";
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public void setDeptYearStatisticsService(
			ClaimVouyearStatisticsService deptYearStatisticsService) {
		this.deptYearStatisticsService = deptYearStatisticsService;
	}

	public void setClaimVoucherService(ClaimVoucherService claimVoucherService) {
		this.claimVoucherService = claimVoucherService;
	}

	public int getCurrYear() {
		return currYear;
	}

	public void setCurrYear(int currYear) {
		this.currYear = currYear;
	}

	public List<ClaimVoucher> getDeptDetailList() {
		return deptDetailList;
	}

	public void setDeptDetailList(List<ClaimVoucher> deptDetailList) {
		this.deptDetailList = deptDetailList;
	}

	public String getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(String detailCount) {
		this.detailCount = detailCount;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	
	
	
	
	
}
