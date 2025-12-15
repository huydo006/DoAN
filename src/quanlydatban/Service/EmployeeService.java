/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Service;

import java.sql.SQLException;
import quanlydatban.Dao.EmployeeDao;
import quanlydatban.Model.Employee;


/**
 *
 * @author Admin
 */
public class EmployeeService {
    EmployeeDao empDao = new EmployeeDao();
    Employee emp=null;
    public Employee getCurrentEmp(String user) throws SQLException{
        emp= empDao.getEmpbyUsername(user);
        return emp;
    }
}
