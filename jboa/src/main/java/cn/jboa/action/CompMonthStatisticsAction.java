package cn.jboa.action;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import cn.jboa.entity.ClaimVoucherStatistics;
import cn.jboa.service.ClaimVoucherService;
import cn.jboa.service.ClaimVoucherStatisticsService;
import cn.jboa.util.ExportExcelUtil;
import cn.jboa.util.JFreeChatUtil;
import cn.jboa.util.NumberFormatUtil;

public class CompMonthStatisticsAction extends BaseAction{
	public ClaimVoucherStatisticsService statisticsService;
	public ClaimVoucherService claimVoucherService;
	
	private int year;
	private int startMonth;
	private int endMonth;
	private int currMonth;
	private String totalCount;
	private JFreeChart chart;
	private List<ClaimVoucherStatistics> compMonthList;
	private List<ClaimVoucherStatistics> compMonthDetail;
	
	public void setStatisticsService(ClaimVoucherStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
	
	public void setClaimVoucherService(ClaimVoucherService claimVoucherService) {
		this.claimVoucherService = claimVoucherService;
	}
	
	public String getList(){
		compMonthList = statisticsService.getCompClaimVoucherByMonth(year, startMonth, endMonth);
		return "list";
	}
	
	public String getDetail(){
		compMonthDetail = claimVoucherService.getClaimVoucherByDateAndDept(year, currMonth);
		Double count = 0.0;
		for(ClaimVoucherStatistics dcv:compMonthDetail){
			count+=dcv.getTotalCount();
		}
		totalCount = NumberFormatUtil.doubleToString(count, "0.00");
		return "detail";
	}
	
	public String getDetailExcel(){
		compMonthDetail = claimVoucherService.getClaimVoucherByDateAndDept(year, currMonth);
		List<String[]> list = new ArrayList<String[]>();
		int i = 0;
		for(ClaimVoucherStatistics cvs:compMonthDetail){
			i++;
			String index = cvs.getDepartment().getId().toString();
			String deptNameCell = cvs.getDepartment().getName().toString();
			
			String totalCell = cvs.getTotalCount().toString();
			String yearCell = new Integer(cvs.getYear()).toString();
			String monthCell = new Integer(cvs.getMonth()).toString();
			
			list.add(new String[]{index,deptNameCell,totalCell,yearCell,monthCell});
		}
		String fileName=year+"年"+currMonth+"月"+"公司月度报销统计";
		try{ 
		    ExportExcelUtil.createExcel(response, list, fileName,"monthly","comp");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "detailExcel";
	}
	
	public String getDetailChart(){
		compMonthDetail = claimVoucherService.getClaimVoucherByDateAndDept(year, currMonth);
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		for(ClaimVoucherStatistics dcv:compMonthDetail){
			dataset.setValue(dcv.getDepartment().getName(), dcv.getTotalCount());
		}
		String title =year+"年"+currMonth+"月"+"公司月度报销统计饼图";
		chart = new JFreeChatUtil().createPieChar(dataset,title);
		return "detailChart";
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}
	public int getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}
	public List<ClaimVoucherStatistics> getCompMonthList() {
		return compMonthList;
	}
	public void setCompMonthList(List<ClaimVoucherStatistics> compMonthList) {
		this.compMonthList = compMonthList;
	}

	public int getCurrMonth() {
		return currMonth;
	}

	public void setCurrMonth(int currMonth) {
		this.currMonth = currMonth;
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

	public List<ClaimVoucherStatistics> getCompMonthDetail() {
		return compMonthDetail;
	}

	public void setCompMonthDetail(List<ClaimVoucherStatistics> compMonthDetail) {
		this.compMonthDetail = compMonthDetail;
	}

		
	
	
	
	
	
	
}
