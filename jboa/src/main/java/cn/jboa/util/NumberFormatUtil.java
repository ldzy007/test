package cn.jboa.util;

import java.text.DecimalFormat;

public class NumberFormatUtil {
	public static String doubleToString(Double num, String style){
		DecimalFormat df = new DecimalFormat();
		df.applyPattern(style);
		return df.format(num);
		
	}
}
