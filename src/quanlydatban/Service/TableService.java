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
    // Không nên khai báo biến toàn cục tbList ở đây nếu muốn dữ liệu luôn mới
    private TableDao tbDao = new TableDao();

    public TableService() {
    }

    // Sửa hàm này để gọi trực tiếp từ DAO mỗi khi cần kiểm tra sức chứa
    public List<Table> getTbList() {
        return tbDao.getAllTable(); 
    }
}
