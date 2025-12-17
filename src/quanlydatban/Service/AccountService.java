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
    
    public String checkLoginAndGetRole(String user, String pass) {
    // Luôn tải lại danh sách mới nhất từ Database để tránh dữ liệu cũ trong bộ nhớ đệm
    this.list = accDao.getAllAcount(); 
    return accDao.getRoleByLogin(user, pass);
}
    public String getRoleByLogin(String user, String pass) {
    return accDao.getRoleByLogin(user, pass);
}

    // Lấy thông tin account để truyền sang Main_menu
    public Account getAccountByUser(String user) {
        return accDao.getAccountByUser(user);
    }

    // Cập nhật trạng thái Active khi đăng nhập/đăng xuất
    public void updateStatus(boolean isActive, String user) {
        accDao.updateActive(isActive, user);
    }

    // Lấy tài khoản đang Active (nếu cần)
    public Account getActiveACc() {
        return accDao.getActiveAccount();
    }

 
    
}
