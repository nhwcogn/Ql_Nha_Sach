/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author letha
 */
public class KhachHangDAO {
    private static Connection con;

    public KhachHangDAO() {
        con = db.DBConnection.getInstance().getConnection();
    }
    
    public String getMaCuoi(){
        String maKH = "";
        String sql = "select * from KhachHang order by Ma_KH desc";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                maKH = rs.getString(1);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maKH;
    }
    public KhachHang timKiem1KhachHangTheoMaKH(String maKH){
        try {
            String sql = "select * from KhachHang where Ma_KH = '"+maKH+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
              KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              return kh;
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public KhachHang timKiem1KhachHangTheoTen(String tenKH){
        try {
            String sql = "select * from KhachHang where TenKH = N'"+tenKH+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
              KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              return kh;
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public ArrayList<KhachHang> timKiemDanhSachKHTheoMa(String maKH){
        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            String sql = "select * from KhachHang where Ma_KH like '%"+maKH+"%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
              KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              
              list.add(kh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<KhachHang> timKiemDanhSachKHTheoTen(String tenKH){
        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            String sql = "select * from KhachHang where TenKH like '%"+tenKH+"%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
              KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              
              list.add(kh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<KhachHang> timKiemDanhSachKHTheoSDT(String sdt){
        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            String sql = "select * from KhachHang where SDT like '%"+sdt+"%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
              KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              
              list.add(kh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<KhachHang> danhSachKhachHang(){
        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            String sql = "select * from KhachHang ";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
              KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              
              list.add(kh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean themKhachHang(KhachHang kh){
        try {
            if(timKiem1KhachHangTheoMaKH(kh.getMaKH()) != null)
                return false;
            String sql = "INSERT INTO [dbo].[KhachHang]\n" +
"           ([Ma_KH]\n" +
"           ,[TenKH]\n" +
"           ,[SDT]\n" +
"           ,[DiaChi]\n" +
"           ,[Email])\n" +
"     VALUES\n" +
"           (? ,? ,? ,? ,? )";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getTenKH());
            stmt.setString(3, kh.getsDT());
            stmt.setString(4, kh.getDiaChi());
            stmt.setString(5, kh.getEmail());
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }  
    }
    public boolean suaKhachHang(KhachHang kh){
        if(timKiem1KhachHangTheoMaKH(kh.getMaKH()) == null)
            return false;
        
        try {
            String sql = "UPDATE [dbo].[KhachHang]\n" +
"   SET [TenKH] = ? " +
"      ,[SDT] = ? " +
"      ,[DiaChi] = ? " +
"      ,[Email] = ? " +
" WHERE [Ma_KH] = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getsDT());
            stmt.setString(3, kh.getDiaChi());
            stmt.setString(4, kh.getEmail());
            stmt.setString(5, kh.getMaKH());
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean xoaKhachHang(String maKH){
        if(timKiem1KhachHangTheoMaKH(maKH)== null)
            return false;
       
        try {
             String sql = "DELETE FROM [dbo].[KhachHang]\n" +
"      WHERE [Ma_KH] = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maKH);
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public ArrayList<String> timKiemDanhSachTenKHTheoKey(String tenKH){
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "select TenKH from KhachHang where TenKH like '%"+tenKH+"%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
              //KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              String kh = rs.getString(1);
              list.add(kh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<KhachHang> dsKHDaMuaHang(){
        String sql = "select * from KhachHang where Ma_KH in (select Ma_KH from HoaDon )";
        ArrayList<KhachHang> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              
              list.add(kh);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    public ArrayList<KhachHang> dsKHDaMuaHangCoDKNgay(Date fromDate, Date toDate){
        String sql = "select * from KhachHang where Ma_KH in \n" +
"(select Ma_KH from HoaDon where NgayLap between  ? and ? )";
        ArrayList<KhachHang> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String startD = dateFormat.format(fromDate);
        String endD = dateFormat.format(toDate);
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, startD);
            stmt.setString(2, endD);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
              
              list.add(kh);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }
    
    
    public int slDonHang(String maKH){
        String sql = "select count(Ma_HoaDon) from HoaDon where Ma_KH = ?";
        
        int sl = -1;
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                sl = rs.getInt(1);
                return sl;
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public KhachHang khachHangCoNhieuDonNhat(){
        String sql = "select top(1) Ma_KH from HoaDon group by Ma_KH order by count(Ma_HoaDon) desc";
        
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                KhachHang kh = timKiem1KhachHangTheoMaKH(rs.getString(1));
                return kh;
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
