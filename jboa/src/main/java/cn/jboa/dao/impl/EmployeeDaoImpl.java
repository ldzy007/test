package cn.jboa.dao.impl;

import java.util.List;

import cn.jboa.common.Constants;
import cn.jboa.dao.EmployeeDao;
import cn.jboa.entity.Employee;

public class EmployeeDaoImpl extends BaseHibernateDaoSupport<Employee> implements EmployeeDao{
	
	private static Employee empGM = null;
	private static Employee empCashier = null;

	public List<Employee> findEmployee(Employee emp) {
		// TODO Auto-generated method stub
		List<Employee> list = getHibernateTemplate().findByExample(emp);
        return list;
	}
	
	public Employee findEmployeeBySn(String sn) {
		// TODO Auto-generated method stub
		return this.get(sn);
	}
	public Employee getManager(Employee employee) {
		// TODO Auto-generated method stub
		String hql = "from Employee e where e.sysDepartment.id=? and e.sysPosition.nameCn=?";
		Employee empManager = (Employee)findFirst(hql, employee.getSysDepartment().getId(),Constants.POSITION_FM);
		return empManager;
	}

	public Employee getGeneralManager() {
		// TODO Auto-generated method stub
		if (empGM == null){
			String hql = "from Employee e where e.sysPosition.nameCn=?";
			empGM = (Employee)findFirst(hql,Constants.POSITION_GM);
		}
		return empGM;
	}

	public Employee getCashier() {
		// TODO Auto-generated method stub
		if (empCashier == null) {
			String hql = "from Employee e where e.sysPosition.nameCn=? and e.status='"
					+ Constants.EMPLOYEE_STAY + "'";
			empCashier = (Employee) findFirst(hql, Constants.POSITION_CASHIER);
		}
		return empCashier;
	}

	

}
