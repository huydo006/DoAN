/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Model;

/**
 *
 * @author HELLO
 */
public class Employee {
    int IdEmploy ;
    String name;
    String DoB;
    String Gender;
    String Phone;
    String Address;
    int Salary;
    String Role;

    public Employee(int IdEmploy, String name, String DoB, String Gender, String Phone, String Address, int Salary, String Role) {
        this.IdEmploy = IdEmploy;
        this.name = name;
        this.DoB = DoB;
        this.Gender = Gender;
        this.Phone = Phone;
        this.Address = Address;
        this.Salary = Salary;
        this.Role = Role;
    }

    public int getIdEmploy() {
        return IdEmploy;
    }

    public void setIdEmploy(int IdEmploy) {
        this.IdEmploy = IdEmploy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String DoB) {
        this.DoB = DoB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int Salary) {
        this.Salary = Salary;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
    
    
}
