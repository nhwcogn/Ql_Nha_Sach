/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.NhaXuatBan;
import entity.Sach;
import entity.SanPham;
import entity.TacGia;
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
public class SachDAO {
    private static Connection con;
    private static SanPhamDAO spDAO = new SanPhamDAO();
    private static NhaXuatBanDAO nxbDAO = new NhaXuatBanDAO();
    private static TacGiaDAO tgDAO = new TacGiaDAO();
    public SachDAO() {
        con = db.DBConnection.getInstance().getConnection();
    }
    public ArrayList<Sach> dsSachTheoMa(String maSach){
        ArrayList<Sach> list = new ArrayList<>();
        try {
            
            String sql = "select * from Sach where Ma_Sach like N'%"+maSach+"%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                SanPham sp = spDAO.timKiemCoSach(rs.getString("Ma_Sach"));
                NhaXuatBan nxb = nxbDAO.timKiemNXB(rs.getString("Ma_NXB"));
                TacGia tg = tgDAO.timKiemTacGia(rs.getString("Ma_TacGia"));
                int soTrang = rs.getInt(2);
                int namXB = rs.getInt(3);
                Sach s = new Sach(sp, soTrang, namXB, tg, nxb);
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<Sach> dsSachTheoTen(String tenSach){
        ArrayList<Sach> list = new ArrayList<>();
        try {
            
            String sql = "select s.* from Sach s inner join SanPham p on s.Ma_Sach = p.Ma_SanPham where p.TenSP like N'%"+tenSach+"%'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                SanPham sp = spDAO.timKiemCoSach(rs.getString("Ma_Sach"));
                NhaXuatBan nxb = nxbDAO.timKiemNXB(rs.getString("Ma_NXB"));
                TacGia tg = tgDAO.timKiemTacGia(rs.getString("Ma_TacGia"));
                int soTrang = rs.getInt(2);
                int namXB = rs.getInt(3);
                Sach s = new Sach(sp, soTrang, namXB, tg, nxb);
                list.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<Sach> dsSach(){
        ArrayList<Sach> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from Sach");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                SanPham sp = spDAO.timKiemCoSach(rs.getString("Ma_Sach"));
                NhaXuatBan nxb = nxbDAO.timKiemNXB(rs.getString("Ma_NXB"));
                TacGia tg = tgDAO.timKiemTacGia(rs.getString("Ma_TacGia"));
                int soTrang = rs.getInt(2);
                int namXB = rs.getInt(3);
                Sach s = new Sach(sp, soTrang, namXB, tg, nxb);
                list.add(s);  
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
        
    }
    public  Sach timKiemSachTheoMa(String maSach){
        try {
            String sql = "select * from Sach where Ma_Sach = '"+maSach+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                SanPham sp = spDAO.timKiemCoSach(maSach);
                NhaXuatBan nxb = nxbDAO.timKiemNXB(rs.getString("Ma_NXB"));
                TacGia tg = tgDAO.timKiemTacGia(rs.getString(4));
                int soTrang = rs.getInt(2);
                int namXB = rs.getInt(3);
                Sach s = new Sach(sp, soTrang, namXB, tg, nxb);
                
                return s;
                
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
    }
    public boolean xoaSach(String maSach){
        if(timKiemSachTheoMa(maSach) == null)
            return false;
        
        try {
            String sql  = "DELETE SanPham  FROM SanPham where SanPham.Ma_SanPham = '"+maSach+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            //stmt.setString(1, maSach);
            int n = stmt.executeUpdate();
            //boolean kqXoaSP = spDAO.xoaSanPham(maSach);
            stmt.close();
            return n>0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean themSach(Sach s){
        if(timKiemSachTheoMa(s.getSanPham().getMa_SanPham()) != null)
            return false;
        String sql = "INSERT INTO [dbo].[Sach]\n" +
"           ([Ma_Sach]\n" +
"           ,[SoTrang]\n" +
"           ,[NamXuatBan]\n" +
"           ,[Ma_TacGia]\n" +
"           ,[Ma_NXB])\n" +
"     VALUES\n" +
"           (? ,? ,? ,? ,? )";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getSanPham().getMa_SanPham());
            stmt.setInt(2, s.getSoTrang());
            stmt.setInt(3, s.getNamSanXuat());
            stmt.setString(4, s.getTacGia().getMaTacGia());
            stmt.setString(5, s.getNhaXuatBan().getMaNhaXuatBan());
            int n = stmt.executeUpdate();
            stmt.close();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public boolean suaSach(Sach s){
        if(timKiemSachTheoMa(s.getSanPham().getMa_SanPham()) == null)
            return false;
        try {
            
            String sql = "UPDATE [dbo].[Sach]\n" +
                    "   SET [SoTrang] = ? " +
                    "      ,[NamXuatBan] = ? " +
                    "      ,[Ma_TacGia] = ? " +
                    "      ,[Ma_NXB] = ? " +
                    " WHERE [Ma_Sach] = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, s.getSoTrang());
            stmt.setInt(2, s.getNamSanXuat());
            stmt.setString(3, s.getTacGia().getMaTacGia());
            stmt.setString(4, s.getNhaXuatBan().getMaNhaXuatBan());
            stmt.setString(5, s.getSanPham().getMa_SanPham());
            //spDAO.suaSanPham(s.getSanPham());
            int n = stmt.executeUpdate();
            stmt.close();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean suaSachDemo(Sach s){
        if(timKiemSachTheoMa(s.getSanPham().getMa_SanPham()) == null)
            return false;
        try {
            
            String sql = "UPDATE [dbo].[SanPham]\n" +
"   SET [TenSP] = ? " +
"      ,[DonGia] = ? " +
"      ,[SoLuong] = ? " +
"      ,[DVTinh] = ? " +
"      ,[ID_LoaiSanPham] = ? " +
" WHERE Ma_SanPham = ? " +
"\n" +
"UPDATE [dbo].[Sach]\n" +
"   SET [SoTrang] = ? " +
"      ,[NamXuatBan] = ? " +
"      ,[Ma_TacGia] = ? " +
"      ,[Ma_NXB] = ? " +
" WHERE Ma_Sach = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, s.getSanPham().getTenSanPham());
            stmt.setDouble(2, s.getSanPham().getDonGia());
            stmt.setInt(3, s.getSanPham().getSoLuong());
            stmt.setString(4, s.getSanPham().getDvTinh());
            stmt.setString(5, s.getSanPham().getLoaiSanPham().getMaLoaiSP());
            stmt.setString(6, s.getSanPham().getMa_SanPham());
            
            stmt.setInt(7, s.getSoTrang());
            stmt.setInt(8, s.getNamSanXuat());
            stmt.setString(9, s.getTacGia().getMaTacGia());
            stmt.setString(10, s.getNhaXuatBan().getMaNhaXuatBan());
            stmt.setString(11, s.getSanPham().getMa_SanPham());
            //spDAO.suaSanPham(s.getSanPham());
            int n = stmt.executeUpdate();
            stmt.close();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
}
