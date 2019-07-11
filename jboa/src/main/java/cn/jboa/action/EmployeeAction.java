package cn.jboa.action;
import org.apache.log4j.Logger;

import cn.jboa.common.Constants;
import cn.jboa.entity.Employee;
import cn.jboa.exception.JboaException;
import cn.jboa.service.EmployeeService;
import cn.jboa.util.MD5;


public class EmployeeAction extends BaseAction{
	static Logger logger = Logger.getLogger(EmployeeAction.class);
	private Employee employee;         // 用户类
    private EmployeeService empService; // 用户业务类
    private String random;
    
    private Employee manager;
    

	public String login(){
		try {
			employee.setPassword(MD5.MD5Encode(employee.getPassword()));//MD5摘要格式密码
			employee = empService.login(employee);
			manager = empService.getManager(employee);
			getSession().put(Constants.AUTH_EMPLOYEE, employee);
			getSession().put(Constants.AUTH_EMPLOYEE_MANAGER, manager);
			getSession().put(Constants.EMPLOYEE_POSITION, employee.getSysPosition().getNameCn());
			logger.debug("employee:" + employee.getSn() + "/"+employee.getPassword()+"/Logined.");
		} catch (JboaException ex) {
			addActionError(getText("errors.invalid.usernameorpassword"));
		} 
		if (hasActionErrors()){
			return INPUT;
		}
		return SUCCESS;
	}
	  
    public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public EmployeeService getEmpService() {
		return empService;
	}

	public void setEmpService(EmployeeService empService) {
		this.empService = empService;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}
	

}
