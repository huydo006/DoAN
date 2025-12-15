/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author HELLO
 */
public class ConnectionDatabase {
    public static Connection getConnection()throws SQLException{
        String DB_URL = "jdbc:mysql://localhost:3306/demo002";
        String USER = "root"; 
        String PASS = "28112006"; 
        
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn; 
    }
}
