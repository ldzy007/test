package cn.jboa.action;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.entity.ClaimVouyearStatistics;
import cn.jboa.service.ClaimVoucherService;
import cn.jboa.service.ClaimVouyearStatisticsService;
import cn.jboa.util.ExportExcelUtil;
import cn.jboa.util.JFreeChatUtil;
import cn.jboa.util.NumberFormatUtil;

public class CompYearStatisticsAction extends BaseAction{
	private ClaimVouyearStatisticsService deptYearStatisticsService;
	private ClaimVoucherService claimVoucherService;
	
	private int startYear;
	private int endYear;
	private int currYear;
	private List<ClaimVouyearStatistics> compYearList;
	private List<ClaimVoucherStatistics> compYearDetail;
	private String totalCount;
	private JFreeChart chart;
	
	public void setDeptYearStatisticsService(
			ClaimVouyearStatisticsService deptYearStatisticsService) {
		this.deptYearStatisticsService = deptYearStatisticsService;
	}
	public void setClaimVoucherService(ClaimVoucherService claimVoucherService) {
		this.claimVoucherService = claimVoucherService;
	}
	
	public String getList(){
		compYearList = deptYearStatisticsService.findCompYearStatistics(startYear, endYear);
		return "list";
	}
	
	public String getDetail(){
		compYearDetail = claimVoucherService.getClaimVoucherByDateAndDept(currYear, 0);
		Double count = 0.0;
		for(ClaimVoucherStatistics dcv:compYearDetail){
			count+=dcv.getTotalCount();
		}
		totalCount = NumberFormatUtil.doubleToString(count, "0.00");
		return "detail";
	}
	
	
	public String getDetailExcel(){
		compYearDetail = claimVoucherService.getClaimVoucherByDateAndDept(currYear, 0);
		List<String[]> list = new ArrayList<String[]>();
		int i = 0;
		for(ClaimVoucherStatistics cvs:compYearDetail){
			i++;
			String index = cvs.getDepartment().getId().toString();
			String deptNameCell = cvs.getDepartment().getName().toString();
			
			String totalCell = cvs.getTotalCount().toString();
			String yearCell = new Integer(cvs.getYear()).toString();
			
			list.add(new String[]{index,deptNameCell,totalCell,yearCell});
		}
		String fileName=currYear+"年公司月度报销统计";
		try{ 
		    ExportExcelUtil.createExcel(response, list, fileName,null,"comp");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "detailExcel";
	}
	
	public String getDetailChart(){
		compYearDetail = claimVoucherService.getClaimVoucherByDateAndDept(currYear, 0);
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		for(ClaimVoucherStatistics dcv:compYearDetail){
			dataset.setValue(dcv.getDepartment().getName(), dcv.getTotalCount());
		}
		String title =currYear+"年公司月度报销统计饼图";
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
	public int getCurrYear() {
		return currYear;
	}
	public void setCurrYear(int currYear) {
		this.currYear = currYear;
	}
	public List<ClaimVouyearStatistics> getCompYearList() {
		return compYearList;
	}
	public void setCompYearList(List<ClaimVouyearStatistics> compYearList) {
		this.compYearList = compYearList;
	}
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public JFreeChart getChart() {
		return chart;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	public List<ClaimVoucherStatistics> getCompYearDetail() {
		return compYearDetail;
	}
	public void setCompYearDetail(List<ClaimVoucherStatistics> compYearDetail) {
		this.compYearDetail = compYearDetail;
	}
	
	
	
	
}
