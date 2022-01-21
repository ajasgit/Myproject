package QueryDetails;

public class EmployeeQuery {

	public static final String ADD_EMPLOYEE = "insert into employee_model (emp_address,emp_age,emp_man,emp_name,emp_salary)"
			+ "values(?,?,?,?,?)";
	public static final String FIND_ALL="SELECT * from employee_model";
	public static final String DELETE_BY_ID = "delete from employee_model where id = ?";
	public static final String FIND_BY_MANAGER_NAME = "SELECT * from employee_model WHERE emp_man=?";
	public static final String FIND_BY_ID = "SELECT * from employee_model WHERE id=?";
	public static final String EMP_UPDATE="UPDATE employee_model SET emp_address = ?, emp_age = ?, emp_man = ? ,emp_name = ?, emp_salary WHERE id = ?";
}
