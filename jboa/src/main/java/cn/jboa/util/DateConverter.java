package cn.jboa.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateConverter extends DefaultTypeConverter{
	
	private static final DateFormat[] ACCEPT_DATE_FORMATS = {
			new SimpleDateFormat("yyyy-MM-dd hh:mm")
	};
	
	public Object convertValue(Map context,Object value,Class toType){
		if (toType == Date.class){
			Date date = null;
			String dateString = null;
			String[] params = (String[])value;
			dateString = params[0];
			for(DateFormat format:ACCEPT_DATE_FORMATS){
				try{
					return format.parse(dateString);
				}catch(Exception e){
					continue;
				}
			}
			return value;
		}else if(toType == String.class){
//			Date date = (Date)value;
//			return new SimpleDateFormat("yyyy-MM-dd").format(date);
			return value;
		}
		return null;
	}

}
