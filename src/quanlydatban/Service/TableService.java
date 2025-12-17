/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlydatban.Service;

import java.util.List;
import quanlydatban.Dao.TableDao;
import quanlydatban.Model.Table;

/**
 *
 * @author HELLO
 */
public class TableService {
    private TableDao tbDao = new TableDao();

    public TableService() {
    }

    public List<Table> getTbList() {
        return tbDao.getAllTable(); 
    }
}
