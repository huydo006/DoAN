/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Service;

import java.sql.SQLException;
import java.util.List;
import quanlydatban.Dao.EmployeeDao;
import quanlydatban.Model.Employee;


/**
 *
 * @author Admin
 */
public class EmployeeService {
    EmployeeDao empDao = new EmployeeDao();
    Employee emp=null;
    List<Employee> list;
    public Employee getCurrentEmp(String user) throws SQLException{
        emp= empDao.getEmpbyUsername(user);
        return emp;
    }
    public List<Employee> getListEMP(){
        return empDao.getListEmployee();
    }
    public boolean RemoveEmp(int id){
        return empDao.DeleteEmp(id);
    }
    public boolean AddEmp(Employee emp){
        return empDao.addEmployee(emp);
    }
    
}
