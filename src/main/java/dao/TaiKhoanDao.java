/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import entity.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author NHU NGOC
 */
public class TaiKhoanDao {
    private Connection connection;

    public TaiKhoanDao() {
         connection =  DBConnection.getInstance().getConnection();
    }
    
    public boolean kiemTraTK(String username, String password)  {
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from TaiKhoan where Ma_NhanVien = ? AND MatKhau = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next(); // Trả về true nếu có dòng khớp, ngược lại trả về false
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Trả về false nếu có lỗi xảy ra hoặc không có dòng khớp
    
    }
    

}
