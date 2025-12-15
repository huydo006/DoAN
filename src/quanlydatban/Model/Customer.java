/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Model;

/**
 *
 * @author HELLO
 */
public class Customer {
    String nameCus;
    String cusPhone;

    public Customer(String nameCus, String cusPhone) {
        this.nameCus = nameCus;
        this.cusPhone = cusPhone;
    }

    public String getNameCus() {
        return nameCus;
    }

    public void setNameCus(String nameCus) {
        this.nameCus = nameCus;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    
    
}
