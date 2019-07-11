package cn.jboa.util;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import cn.jboa.common.Util;

public class ExportExcelUtil {
	
	public static void createExcel(HttpServletResponse response, List pageDataList, String fileName,String dateType,String orgType){
		try {
			OutputStream out = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; fileName=" + new String((fileName+".xls").getBytes(), "iso8859-1"));
			WritableWorkbook wbook = Workbook.createWorkbook(out); // 建立excel文件
			WritableSheet wsheet = wbook.createSheet("导出数据", 0); // sheet名称
			WritableCellFormat cellFormatNumber = new WritableCellFormat();
			cellFormatNumber.setAlignment(Alignment.RIGHT);
			
			 WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
	        	     WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
	        	     jxl.format.Colour.BLACK); // 定义格式、字体、粗体、斜体、下划线、颜色
	        WritableCellFormat wcf = new WritableCellFormat(wf); // title单元格定义
	        WritableCellFormat wcfc = new WritableCellFormat(); // 一般单元格定义
	        WritableCellFormat wcfe = new WritableCellFormat(); // 一般单元格定义
	        wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
	        wcfc.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式
	        
	        wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
	        wcfc.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
	        wcfe.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
	        
	        wsheet.setColumnView(0, 20);//设置列宽
	        wsheet.setColumnView(1, 10);
	        wsheet.setColumnView(2, 20);
	        
			int rowIndex = 0;
			int columnIndex = 0;
			if (null != pageDataList) {
					//rowIndex++;
					columnIndex = 0;
					wsheet.setRowView(rowIndex, 500);//设置标题行高
					wsheet.addCell(new Label(columnIndex++, rowIndex, fileName,wcf));
					if("dept".equals(orgType)){
						wsheet.mergeCells(0, rowIndex, "monthly".equals(dateType) ? 5 : 4 , rowIndex);//合并标题所占单元格
					}else{
						wsheet.mergeCells(0, rowIndex, "dept".equals(dateType) ? 4 : 3 , rowIndex);//合并标题所占单元格
					}
					//wsheet.mergeCells(0, rowIndex, "monthly".equals(dateType) ? 5 : 4 , rowIndex);//合并标题所占单元格
					rowIndex++;
					columnIndex = 0;
					wsheet.setRowView(rowIndex, 380);//设置项目名行高
					if("dept".equals("orgType")){
						wsheet.addCell(new Label(columnIndex++, rowIndex, "编号",wcf));
						wsheet.addCell(new Label(columnIndex++, rowIndex, "报销人",wcf));
						wsheet.addCell(new Label(columnIndex++, rowIndex, "报销总额",wcf));
						wsheet.addCell(new Label(columnIndex++, rowIndex, "年份",wcf));
						if("monthly".equals(dateType)){
							wsheet.addCell(new Label(columnIndex++, rowIndex, "月份",wcf));
						}
						wsheet.addCell(new Label(columnIndex++, rowIndex, "部门",wcf));
					}else{
						wsheet.addCell(new Label(columnIndex++, rowIndex, "部门编号",wcf));
						wsheet.addCell(new Label(columnIndex++, rowIndex, "部门",wcf));
						wsheet.addCell(new Label(columnIndex++, rowIndex, "报销总额",wcf));
						wsheet.addCell(new Label(columnIndex++, rowIndex, "年份",wcf));
						if("monthly".equals(dateType)){
							wsheet.addCell(new Label(columnIndex++, rowIndex, "月份",wcf));
						}
					}
					/*wsheet.addCell(new Label(columnIndex++, rowIndex, "编号",wcf));
					wsheet.addCell(new Label(columnIndex++, rowIndex, "报销人",wcf));
					wsheet.addCell(new Label(columnIndex++, rowIndex, "报销总额",wcf));
					wsheet.addCell(new Label(columnIndex++, rowIndex, "年份",wcf));
					if("monthly".equals(dateType)){
						wsheet.addCell(new Label(columnIndex++, rowIndex, "月份",wcf));
					}
					wsheet.addCell(new Label(columnIndex++, rowIndex, "部门",wcf));*/
					// 开始行循环
						for (Object objt : pageDataList) { // 循环列
							Object[] array = (Object[])objt;
							rowIndex++;
							columnIndex = 0;
							
							wsheet.addCell(new Label(columnIndex++, rowIndex, Util.nvl(array[0]),wcfe));
							wsheet.addCell(new Label(columnIndex++, rowIndex, Util.nvl(array[1]),wcfe));
							wsheet.addCell(new Label(columnIndex++, rowIndex, Util.nvl(array[2]),wcfe));
							wsheet.addCell(new Label(columnIndex++, rowIndex, Util.nvl(array[3]),wcfe));
							if("dept".equals(orgType)){
								wsheet.addCell(new Label(columnIndex++, rowIndex, Util.nvl(array[4]),wcfe));
								if("monthly".equals(dateType)){
									wsheet.addCell(new Label(columnIndex++, rowIndex, Util.nvl(array[5]),wcfe));
								}
							}else{
								if("monthly".equals(dateType)){
									wsheet.addCell(new Label(columnIndex++, rowIndex, Util.nvl(array[4]),wcfe));
								}
							}
							
						}
						rowIndex++;
						columnIndex = 0;
			}
			wbook.write();
			if (wbook != null) {
				wbook.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
}
