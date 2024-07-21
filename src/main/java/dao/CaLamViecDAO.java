/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import entity.CaLamViec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NHU NGOC
 */
public class CaLamViecDAO {
    private Connection connection;
    
    public CaLamViecDAO() {
        connection =  DBConnection.getInstance().getConnection();
    }
    
    public ArrayList<CaLamViec> getListCaLamViec(){
    	ArrayList<CaLamViec> DSCa = new ArrayList<>();
    	try {
			Statement stmt = connection.createStatement();
                        ResultSet rs = stmt.executeQuery("select * from CaLamViec");
			while(rs.next()) {
		
                                CaLamViec caLamViec = new CaLamViec( rs.getString("Ma_Ca"),
                                        rs.getString("TenCa"));
                                    
                                   DSCa.add(caLamViec); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return DSCa;
    }
    
    public CaLamViec timCaLam(String maCa)  {
        
            PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("select * from CaLamViec where Ma_Ca like ?");
            stmt.setString(1, "%" + maCa + "%");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                CaLamViec ca = new CaLamViec(rs.getString("Ma_Ca"),
                        rs.getString("TenCa")
                );
                return ca;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaLamViecDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           
       
    return null;
    }
    
    public CaLamViec timCaLamVoiTen(String tenCa)  {
        
            PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement("select * from CaLamViec where TenCa like ?");
            stmt.setString(1, tenCa);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                CaLamViec ca = new CaLamViec(rs.getString("Ma_Ca"),
                        rs.getString("TenCa")
                );
                return ca;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaLamViecDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           
       
    return null;
    }
}
