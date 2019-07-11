package cn.jboa.action;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import cn.jboa.common.Constants;
import cn.jboa.entity.Employee;
import cn.jboa.util.PaginationSupport;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class BaseAction extends ActionSupport implements ServletResponseAware{
	public HttpServletResponse response;
	
	protected Integer pageNo = 1;
	protected Integer pageSize = 5;
	
	protected PaginationSupport pageSupport;
	
	protected Map getSession(){
		ActionContext actionContext = ActionContext.getContext();
		return actionContext.getSession();
	}
	protected Map getRequest(){
		ActionContext actionContext = ActionContext.getContext();
		Map request = (Map)actionContext.get("request");
		return request;
	}
	protected Employee getLoginEmployee(){
		if (getSession().get(Constants.AUTH_EMPLOYEE)==null){
			return null;
		}else{
			return (Employee)getSession().get(Constants.AUTH_EMPLOYEE);
		}
	}
	
	protected boolean isStaff(){
		Employee employee = getLoginEmployee();
		if (employee.getSysPosition().getNameCn().equals(Constants.POSITION_STAFF)){
			return true;
		}else{
			return false;
		}
	}
	
	protected String getCurrentSn(){
		Employee employee = getLoginEmployee();
		return employee.getSn();
	}
	protected  String getCurrManagerSn(){
		Employee employee = (Employee)getSession().get(Constants.AUTH_EMPLOYEE_MANAGER);
		return employee.getSn();
	}
	
	public void setServletResponse(HttpServletResponse resp) {
		this.response = resp;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public PaginationSupport getPageSupport() {
		return pageSupport;
	}
	public void setPageSupport(PaginationSupport pageSupport) {
		this.pageSupport = pageSupport;
	}
	
	
}
