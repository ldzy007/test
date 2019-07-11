package cn.jboa.dao;

import java.util.List;

import cn.jboa.entity.Employee;

public interface EmployeeDao extends BaseDao<Employee>{
	
	public List<Employee> findEmployee(Employee emp);
	
	public Employee findEmployeeBySn(String sn);
	public Employee getManager(Employee employee);
	
	public Employee getGeneralManager();
	
	public Employee getCashier();

}
