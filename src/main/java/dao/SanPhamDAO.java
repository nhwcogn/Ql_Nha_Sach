/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.LoaiSanPham;
import entity.NhaCungCap;
import entity.SanPham;
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
public class SanPhamDAO {

    private static Connection con;
    public SanPhamDAO() {
        con = db.DBConnection.getInstance().getConnection();
    }
    public ArrayList<SanPham> timDanhSachSanPhamTheoMaSP(String maSP){
        ArrayList<SanPham> list = new ArrayList<>();
        try {
            //String sql = "select s.* from SanPham where Ma_SanPham = '"+maSP+"'";
            PreparedStatement stmt = con.prepareStatement("select * from SanPham s inner join LoaiSanPham l on s.ID_LoaiSanPham = l.Ma_LoaiSanPham where l.LoaiSP <> 'Sách' and s.Ma_SanPham like N'%"+maSP+"%'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                list.add(sp);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<SanPham> timDanhSachSanPhamTheoTenSP(String tenSP){
        ArrayList<SanPham> list = new ArrayList<>();
        try {
            //String sql = "select s.* from SanPham where Ma_SanPham = '"+maSP+"'";
            PreparedStatement stmt = con.prepareStatement("select * from SanPham s inner join LoaiSanPham l on s.ID_LoaiSanPham = l.Ma_LoaiSanPham where l.LoaiSP <> 'Sách' and s.TenSP like N'%"+tenSP+"%'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                list.add(sp);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<String> timDSTenSanPhamTheokey(String tenSP){
        ArrayList<String> list = new ArrayList<>();
        try {
            //String sql = "select s.* from SanPham where Ma_SanPham = '"+maSP+"'";
            PreparedStatement stmt = con.prepareStatement("select TenSP from SanPham where TenSP like N'%"+tenSP+"%'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String ten = rs.getString(1);
                list.add(ten);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<SanPham> timDanhSachSanPham(){
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "select s.* from SanPham s inner join LoaiSanPham l on s.ID_LoaiSanPham = l.Ma_LoaiSanPham where l.LoaiSP <> 'Sách' ";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                list.add(sp);
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public LoaiSanPham timKiemLSPTheoMa(String maLSP){
        //String sql = "select * from LoaiSanPham  where Ma_LoaiSanPham = '"+maLSP+"'";
        try {
             PreparedStatement stmt = con.prepareStatement("select * from LoaiSanPham  where Ma_LoaiSanPham = N'"+maLSP+"'");
            //stmt.setString(1, maLSP);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                LoaiSanPham lsp = new LoaiSanPham(rs.getString(1), rs.getString(2), rs.getString(3));
                return  lsp;
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String timNhaCungCapTheoMaSP(String maSP){
        String tenNCC = null;
        try {
            PreparedStatement stmt = con.prepareStatement("select TenNCC from NhaCungCap  ncc\n" +
                "inner join PhieuMuaSanPham p on ncc.Ma_NhaCungCap = p.Ma_NCC\n" +
                "inner join CTPhieuMuaSanPham ctp on p.Ma_PhieuNhapSP = ctp.Ma_PhieuNhapSP\n" +
                "inner join SanPham s on ctp.Ma_SanPham = s.Ma_SanPham\n" +
                "where s.Ma_SanPham = '"+maSP+"'");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                tenNCC = rs.getString(1);
            }
            stmt.close();
        } catch (SQLException e) {
        }
        return tenNCC;
        
    }
    public NhaCungCap timNCC(String maSP){
        
        try {
            PreparedStatement stmt = con.prepareStatement("select ncc.* from NhaCungCap  ncc\n" +
                "inner join PhieuMuaSanPham p on ncc.Ma_NhaCungCap = p.Ma_NCC\n" +
                "inner join CTPhieuMuaSanPham ctp on p.Ma_PhieuNhapSP = ctp.Ma_PhieuNhapSP\n" +
                "inner join SanPham s on ctp.Ma_SanPham = s.Ma_SanPham\n" +
                "where s.Ma_SanPham = '"+maSP+"'");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                NhaCungCap ncc = new NhaCungCap(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                return ncc;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;   
    }
    public SanPham timKiem(String maSP){
        try {
            String sql = "select s.* from SanPham s inner join LoaiSanPham l on s.ID_LoaiSanPham = l.Ma_LoaiSanPham where l.LoaiSP <> 'Sách' and s.Ma_SanPham = '"+maSP+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                return sp;
                
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public SanPham timKiemCoSach(String maSP){
        try {
            String sql = "select * from SanPham where Ma_SanPham = '"+maSP+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                return sp;
                
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public SanPham timKiem1SanPhamTheoTen(String tenSP){
        try {
            String sql = "select * from SanPham where TenSP = N'"+tenSP+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                return sp;
                
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean themSanPham(SanPham sp){
        try {
            if(timKiem(sp.getMa_SanPham()) != null)
                return false;
            String sql = "INSERT INTO [dbo].[SanPham]\n" +
"           ([Ma_SanPham]\n" +
"           ,[TenSP]\n" +
"           ,[DonGia]\n" +
"           ,[SoLuong]\n" +
"           ,[DVTinh]\n" +
"           ,[ID_LoaiSanPham])\n" +
"     VALUES\n" +
"           (? ,? ,? ,? ,? ,? )";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sp.getMa_SanPham());
            stmt.setString(2, sp.getTenSanPham());
            stmt.setDouble(3, sp.getDonGia());
            stmt.setInt(4, sp.getSoLuong());
            stmt.setString(5, sp.getDvTinh());
            stmt.setString(6, sp.getLoaiSanPham().getMaLoaiSP());
            int n  = stmt.executeUpdate();
            stmt.close();
            return n>0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean suaSanPham(SanPham sp){
        try {
            if(timKiem(sp.getMa_SanPham()) == null)
                return false;
            String sql = "UPDATE [dbo].[SanPham]\n" +
"   SET [TenSP] = ? " +
"      ,[DonGia] = ? " +
"      ,[SoLuong] = ? " +
"      ,[DVTinh] = ? " +
"      ,[ID_LoaiSanPham] = ? " +
" WHERE  Ma_SanPham = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sp.getTenSanPham());
            stmt.setDouble(2, sp.getDonGia());
            stmt.setInt(3, sp.getSoLuong());
            stmt.setString(4, sp.getDvTinh());
            stmt.setString(5, sp.getLoaiSanPham().getMaLoaiSP());
            stmt.setString(6, sp.getMa_SanPham());
            int n = stmt.executeUpdate();
            stmt.close();
            return n>0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean xoaSanPham(String maSP){
        if(timKiem(maSP) == null)
            return false;
        try {
            String sql = "DELETE FROM [dbo].[SanPham]\n" +
"      WHERE Ma_SanPham  = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maSP);
            int n = stmt.executeUpdate();
            stmt.close();
            return n>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    public String getMaCuoi(){
        String maSP = "";
        String sql = "select * from SanPham order by Ma_SanPham desc";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                maSP = rs.getString(1);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maSP;
    }
    
    public ArrayList<SanPham> danhSachSPDaBan(){
        String sql = "select * from SanPham where Ma_SanPham in (select Ma_SanPham from CTHoaDon)";
        ArrayList<SanPham> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                list.add(sp);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public ArrayList<SanPham> danhSachSPDaBanCoDKNgay(Date fromDate, Date toDate){
        String sql = "select * from SanPham \n" +
                     "where Ma_SanPham in (select Ma_SanPham from CTHoaDon c \n" +
                                           "inner join HoaDon h on c.Ma_HoaDon = h.Ma_HoaDon \n" +
                                           "where h.NgayLap between  ? and ? )";
        ArrayList<SanPham> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String startD = dateFormat.format(fromDate);
        String endD = dateFormat.format(toDate);
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setString(1, startD);
            stmt.setString(2, endD);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                LoaiSanPham lsp = timKiemLSPTheoMa(rs.getString("ID_LoaiSanPham"));
                SanPham sp = new SanPham(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getInt(4),
                        rs.getString(5),
                        lsp);
                list.add(sp);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int tongSLSanPham(String maSP){
        String sql = "select sum(SoLuong) as total from CTHoaDon where Ma_SanPham = ?";
        int sl = 0;
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                sl = rs.getInt(1);
            return sl;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public double tongGiaTien(String maSP){
        String sql = "select sum(SoLuong*DonGia) as total from CTHoaDon where Ma_SanPham = ?";
        double sl = 0;
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                sl = rs.getDouble(1);
            return sl;
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public SanPham spCoSLBanDuocNhieuNhat(){
        String sql = "select top(1) Ma_SanPham from CTHoaDon group by Ma_SanPham order by sum(SoLuong) DESC";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                SanPham sp = timKiemCoSach(rs.getString(1));
                return sp;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public SanPham spCoGiaBanCaoNhat(){
        String sql = "select top(1) Ma_SanPham from CTHoaDon \n" +
"group by Ma_SanPham \n" +
"order by sum(SoLuong*DonGia) DESC ";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                SanPham sp = timKiemCoSach(rs.getString(1));
                return sp;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
