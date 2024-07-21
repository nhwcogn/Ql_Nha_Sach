/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author letha
 */
import db.DBConnection;
import entity.TacGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TacGiaDAO {

    private static Connection connection;
    public TacGiaDAO() {
        connection = DBConnection.getInstance().getConnection();
    }
    
    public ArrayList<TacGia> getDanhSachTacGia(){
        
        ArrayList<TacGia> listTacGia = new ArrayList<>();
        String sql = "select * from TacGia";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                TacGia tg = new TacGia(rs.getString(1), rs.getString(2));
                listTacGia.add(tg);
            }
             stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTacGia;
    }
    // tim kiem danh sach tac gia theo ten
    public ArrayList<TacGia> timKiemTacGiaTheoTen(String tenTG){
        ArrayList<TacGia> list = new ArrayList<>();
        try {
            String sql = "select * from TacGia where TenTacGia like N'%"+tenTG+"%'";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                TacGia tg = new TacGia(rs.getString(1), rs.getString(2));
                list.add(tg);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    // tim kiem danh sach tac gia theo ma
    public  ArrayList<TacGia> timKiemTacGiaTheoMa(String maTG){
        ArrayList<TacGia> list = new ArrayList<>();
        try {
            String sql = "select * from TacGia where Ma_TacGia like N'%"+maTG+"%' ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            //stmt.setString(1, maTG);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                TacGia tg = new TacGia(rs.getString(1), rs.getString(2));
                list.add(tg);
            }
             stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    //tim kiem  1 tac gia the ma
    public  TacGia timKiemTacGia(String maTG){
        try {
            String sql = "select * from TacGia where Ma_TacGia = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maTG);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                TacGia tg = new TacGia(rs.getString(1), rs.getString(2));
                return tg;
            }
             stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    // them tac gia
    public  boolean themTacGia(TacGia newTacGia)  {
        try {
            if(timKiemTacGia(newTacGia.getMaTacGia()) != null)
                return false;
            String sql = "INSERT INTO [dbo].[TacGia]\n" +
"           ([Ma_TacGia]\n" +
"           ,[TenTacGia])\n" +
"     VALUES\n" +
"           ( ? , ? )";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, newTacGia.getMaTacGia());
            stmt.setString(2, newTacGia.getTenTacGia());
            int n = stmt.executeUpdate();
             stmt.close();
            return n >0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    // cap nhat thong tin tac gia
    public  boolean suaTacGia(TacGia tacGia)  {
        try {
            if(timKiemTacGia(tacGia.getMaTacGia()) == null)
                return false;
            String sql ="UPDATE [dbo].[TacGia] " +
                    "   SET [TenTacGia] = ? " +
                    "   WHERE Ma_TacGia = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tacGia.getTenTacGia());
            stmt.setString(2, tacGia.getMaTacGia());
            int n = stmt.executeUpdate();
             stmt.close();
            return n > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    // xoa 1 tac gia 
    public  boolean xoaTacGia(String maTG)  {
        try {
            if(timKiemTacGia(maTG) == null)
                return false;
            String sql  ="DELETE FROM [dbo].[TacGia] " +
                    "     WHERE Ma_TacGia = ? ";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, maTG);
            int n = stmt.executeUpdate();
             stmt.close();
            return n > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    //tim danh sach tac gia co chua ten 
    public ArrayList<TacGia> timDSTacGiaTheoMa(String ma){
        ArrayList<TacGia> list = new ArrayList<>();
        String sql = "select * from TacGia where Ma_TacGia like N'%?%'";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, ma);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                TacGia tg = new TacGia(rs.getString(1), rs.getString(2));
                list.add(tg);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    public String getMaTGCuoi(){
        String maTG = "";
        String sql = "select * from TacGia order by Ma_TacGia desc";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                maTG = rs.getString("Ma_TacGia");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maTG;
    }
    public TacGia timKiem1TGTheoTen(String tenTG){
        try {
            String sql = "select * from TacGia where TenTacGia = N'"+tenTG+"'";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                TacGia tg = new TacGia(rs.getString(1), rs.getString(2));
                return tg;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public ArrayList<String> dsTenTG(){
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "select TenTacGia from TacGia";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String ten = rs.getString(1);
                list.add(ten);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TacGiaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
}
