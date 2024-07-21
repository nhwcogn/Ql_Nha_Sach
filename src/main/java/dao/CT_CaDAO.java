/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import entity.CaLamViec;
import entity.CT_Ca;
import entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author NHU NGOC
 */
public class CT_CaDAO {
    private Connection connection;
    
    public CT_CaDAO() {
        connection =  DBConnection.getInstance().getConnection();
    }
    
    public ArrayList<CT_Ca> getListCTCa(){
    	ArrayList<CT_Ca> listCTCa = new ArrayList<>();
    	try {
			Statement stmt = connection.createStatement();
                        ResultSet rs = stmt.executeQuery("select * from ct_Ca");
			while(rs.next()) {
				
				Date ngayLam = rs.getDate("NgayLam");
                                CT_Ca ct_Ca = new CT_Ca(new CaLamViec(rs.getString("Ma_Ca")), 
                                        new NhanVien(rs.getString("Ma_NV")), 
                                        ngayLam);
                                    
                                   listCTCa.add(ct_Ca); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return listCTCa;
    }
    
    public boolean themChiTietCa(String maCa, String maNhanVien, Date ngayLam){
        String sql = "INSERT INTO ct_Ca(Ma_Ca, Ma_NV, NgayLam) VALUES (?, ?, ?)";

        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maCa);
            stmt.setString(2, maNhanVien);
            stmt.setDate(3, new java.sql.Date(ngayLam.getTime()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
    

   