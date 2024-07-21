/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.LoaiSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author letha
 */
public class LoaiSanPhamDAO {
    private static Connection con;
    public LoaiSanPhamDAO() {
        con = db.DBConnection.getInstance().getConnection();
    }
    
    public LoaiSanPham timKiemTheoTen(String tenLSP){
        try {
            String sql = "select * from LoaiSanPham where TenLoai like N'%"+tenLSP+"%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                LoaiSanPham lsp = new LoaiSanPham(rs.getString(1), rs.getString(2), rs.getString(3));
                return lsp;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public LoaiSanPham timKiemTheoMa(String maLSP){
        String sql = "select * from LoaiSanPham where Ma_LoaiSanPham = '"+maLSP+"'";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                LoaiSanPham lsp = new LoaiSanPham(rs.getString(1), rs.getString(2), rs.getString(3));
                return lsp;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<LoaiSanPham> timKiemDanhSachTheoMa(String maLSP){
        String sql = "select * from LoaiSanPham where Ma_LoaiSanPham like N'%"+maLSP+"%'";
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LoaiSanPham lsp = new LoaiSanPham(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(lsp);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<LoaiSanPham> timKiemDanhSachTheoTen(String tenLSP){
        String sql = "select * from LoaiSanPham where TenLoai like N'%"+tenLSP+"%'";
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        try {
            
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LoaiSanPham lsp = new LoaiSanPham(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(lsp);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<LoaiSanPham> danhSachLSP(){
        String sql = "select * from LoaiSanPham ";
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        try {
            
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LoaiSanPham lsp = new LoaiSanPham(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(lsp);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean themLoaiSanPham(LoaiSanPham lsp){
        if(timKiemTheoMa(lsp.getMaLoaiSP()) != null)
            return false;
        String sql = "INSERT INTO [dbo].[LoaiSanPham]\n" +
"           ([Ma_LoaiSanPham]\n" +
"           ,[LoaiSP]\n" +
"           ,[TenLoai])\n" +
"     VALUES\n" +
"           ( ? ,? , ? )";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, lsp.getMaLoaiSP());
            stmt.setString(2, lsp.getLoaiSP());
            stmt.setString(3, lsp.getTenLoai());
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public boolean suaLoaiSanPham(LoaiSanPham lsp){
        if(timKiemTheoMa(lsp.getMaLoaiSP()) == null)
            return false;
        String sql = "UPDATE [dbo].[LoaiSanPham]\n" +
"   SET [LoaiSP] = ? " +
"      ,[TenLoai] = ? " +
" WHERE Ma_LoaiSanPham = ? ";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, lsp.getLoaiSP());
            stmt.setString(2, lsp.getTenLoai());
            stmt.setString(3, lsp.getMaLoaiSP());
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public boolean xoaLoaiSanPham( String maLSP){
        if(timKiemTheoMa(maLSP) == null)
            return false;
        String sql = "DELETE FROM [dbo].[LoaiSanPham]\n" +
"      WHERE Ma_LoaiSanPham = ? ";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maLSP);
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public String getMaCuoi(){
        String maLSP = "";
        String sql = "select * from LoaiSanPham order by Ma_LoaiSanPham desc";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                maLSP = rs.getString(1);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maLSP;
    }
    public ArrayList<String> layDSLoaiSanPham(){
        ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "select LoaiSP from LoaiSanPham";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String ten = rs.getString(1);
                list.add(ten);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  list;
    }
    public ArrayList<String> danhSachTenLoaiSPTheoLSP(String lsp){
         ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "select TenLoai from LoaiSanPham where LoaiSP = N'"+lsp+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String tenLoaiSP = rs.getString(1);
                list.add(tenLoaiSP);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<String> danhSachMaLoaiSPTheoTenSP(String tenlsp){
         ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "select Ma_LoaiSanPham from LoaiSanPham where TenLoai = N'"+tenlsp+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String tenLoaiSP = rs.getString(1);
                list.add(tenLoaiSP);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoaiSanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
