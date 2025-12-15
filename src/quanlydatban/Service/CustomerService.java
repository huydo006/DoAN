/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Service;

import quanlydatban.Dao.CustomerDao;
import quanlydatban.Model.Customer;

/**
 *
 * @author Admin
 */
public class CustomerService {
    CustomerDao CusDao = new CustomerDao();
    Customer cus = null;
    public boolean addCus(String name , String phone){
        return CusDao.addCustomer(name, phone);
    }
    public int getIdCus( String phone){
        return CusDao.getIdCus(phone);
    }
    public Customer getCus(int id){
        cus= CusDao.getCusById(id);
        return cus;
    }
    public void DeleteCus(int id){
        CusDao.DeleteCus(id);
    }
}
