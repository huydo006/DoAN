/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Service;

import java.util.List;
import quanlydatban.Dao.AccountDao;
import quanlydatban.Model.Account;
import quanlydatban.View.dangnhap.JFLoginUI;

/**
 *
 * @author HELLO
 */
public class AccountService {
    public List<Account> list;
    AccountDao accDao= new AccountDao();
    Account acc= null;

    public AccountService() {
        this.list = accDao.getAllAcount();
    }
    
    public List<Account> getAccountList() {
        this.list=accDao.getAllAcount();
        return list;
    }
    public boolean checkLogin(String user , String pass){
        for(Account x: list){
            if(user.equalsIgnoreCase(x.username) && pass.equalsIgnoreCase(x.password)){
                x.isActive= true;
                accDao.updateActive(true, user);
                return true;
            }
        }
        return false;
    }
    public Account getAccbyUser(String user){
        acc= accDao.getAccountByUser(user);
        return acc;
    }
    
}
