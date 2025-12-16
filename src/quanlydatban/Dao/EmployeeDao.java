package quanlydatban.Dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import quanlydatban.Model.Employee;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author HELLO
 */
public class EmployeeDao {
    List<Employee> list = new ArrayList<>();
   
    public List<Employee> getListEmployee(){
        try {
            String sql = "select IDemploy , Name, DoB , Gender , Phone, Address, Salary, role from employee";
            Connection conn = ConnectionDatabase.getConnection();
            
            Statement stm= conn.createStatement();
            ResultSet rs= stm.executeQuery(sql);
            list.clear();
            while(rs.next()){
                int IdEmploy = rs.getInt("IDemploy");
                String name = rs.getString("Name");
                String DoB = rs.getString("DoB");
                String Gender = rs.getString("Gender");
                String Phone = rs.getString("Phone");
                String Address = rs.getString("Address");
                int Salary = rs.getInt("Salary");
                String Role= rs.getString("role");
                list.add(new Employee(IdEmploy,name, DoB, Gender, Phone,  Address, Salary, Role));
            }
            return list;
        } catch (SQLException ex) {
            System.getLogger(EmployeeDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    
    public boolean isManager(String users) throws SQLException{
        String sql = "select username, role from employee where username =? ";
               
        Connection conn = ConnectionDatabase.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ps.setString(1,users);
        ResultSet rs= ps.executeQuery();
        
        String getRole = "";
        if(rs.next()){
            getRole = rs.getString("role");
            if(getRole.equalsIgnoreCase("Manager")){
            return true;
            }
        }
        
        return false;
    }
    
    public Employee getEmpbyUsername(String username) {
        Employee emp = null;
        
        String sql= "Select IDemploy ,Name,DoB , Gender , Phone , Address , Salary, role from Employee where username = ?";
                
        
        Connection conn;
        try {
            conn = ConnectionDatabase.getConnection();
            PreparedStatement psm = conn.prepareStatement(sql);
            
        System.out.println("DEBUG: Đang tìm nhân viên có username = " + username);
        psm.setString(1, username);
        ResultSet rs= psm.executeQuery();
        
        while(rs.next()){
            int id = rs.getInt("IDemploy");
            String name = rs.getString("Name");
            String dob = rs.getString("DoB");
            String gender =rs.getString("Gender");
            String phone = rs.getString("Phone");
            String address = rs.getString("Address");
            int salary =rs.getInt("Salary");
            String role = rs.getString("role");
            
            emp= new Employee(id, name , dob, gender , phone , address , salary , role);
        }
        } catch (SQLException ex) {
            System.getLogger(EmployeeDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return emp;
    }
    public boolean addEmployee(Employee emp)  {
        try {
            String sql = "INSERT INTO employee ( Name, DoB, Gender, Phone, Address, Salary, Role) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
            
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            
            
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getDoB());
            ps.setString(3, emp.getGender());
            ps.setString(4, emp.getPhone());
            ps.setString(5, emp.getAddress());
            ps.setInt(6, emp.getSalary());
            ps.setString(7, emp.getRole());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            System.getLogger(EmployeeDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;

    }
    public boolean DeleteEmp(int IdEmp){
        try {
            String sql= "DELETE FROM Employee " +
                    "WHERE IDemploy = ?;";
            Connection conn = ConnectionDatabase.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, IdEmp);
            pstm.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            System.getLogger(EmployeeDao.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
        
    }
    
}
