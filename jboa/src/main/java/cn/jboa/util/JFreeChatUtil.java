package cn.jboa.util;

import java.awt.Font;
import java.io.FileOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChatUtil {
	public JFreeChart createBarChar(){
		//数据集
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		dataSet.addValue(100, "北京", "西瓜");
		dataSet.addValue(200, "北京", "苹果");
		dataSet.addValue(400, "北京", "桃子");
		dataSet.addValue(1000, "南京", "西瓜");
		dataSet.addValue(300, "南京", "苹果");
		dataSet.addValue(500, "南京", "桃子");
		dataSet.addValue(700, "天津", "西瓜");
		dataSet.addValue(200, "天津", "苹果");
		dataSet.addValue(600, "天津", "桃子");
		
		//
		ChartFactory.setChartTheme(setTheme());
		JFreeChart chart = ChartFactory.createBarChart3D("水果产量", "水果", "产量", dataSet, PlotOrientation.VERTICAL, true,	false, false);
		
		//处理中文乱码
		/*CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
		axis.setLabelFont(new Font("宋体",Font.PLAIN,15));
		ValueAxis valueAxis = chart.getCategoryPlot().getRangeAxis();
		valueAxis.setLabelFont(new Font("宋体",Font.PLAIN,15));*/
//		createImage(chart,"chart.jpg");
		
		return chart;
	}
	
	public JFreeChart createPieChar(DefaultPieDataset dataset, String title){
		//数据集
//		dataset = new DefaultPieDataset(); 
		
		ChartFactory.setChartTheme(setTheme());
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
//		createImage(chart,"piechart.jpg");
		return chart;
		
	}
	
	public StandardChartTheme setTheme(){
		//处理中文乱码
		StandardChartTheme chartTheme = new StandardChartTheme("CN");
		//标题
		chartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
		//图例
		chartTheme.setRegularFont(new Font("宋体",Font.PLAIN,15));
		//轴向字体
		chartTheme.setLargeFont(new Font("宋体",Font.PLAIN,15));
		return chartTheme;
	}
	
	public void createImage(JFreeChart chart, String fileName){
		FileOutputStream image = null;
		try{
			image = new FileOutputStream(fileName);
			ChartUtilities.writeChartAsJPEG(image, chart, 400, 300);
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void createImageAsWeb(){
	}
	public static void main(String[] args){
//		new JFreeChatUtil().createBarChar();
//		new JFreeChatUtil().createPieChar();
	}
}
