
package dao;

import db.DBConnection;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author letha
 */
public class HoaDonDAO {
    private Connection con;
    private NhanVienDAO nvDao = new NhanVienDAO();
    private KhachHangDAO khDAO = new KhachHangDAO();
    public HoaDonDAO() {
        con = DBConnection.getInstance().getConnection();
    }
    
    public String getMaCuoi(){
        
        try {
            
            String sql = "select Ma_HoaDon from HoaDon order by Ma_HoaDon desc";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
               String maCuoi = rs.getString(1);
               return maCuoi;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public double tinhPhiVAT(double  donGia){
        return donGia*0.05;
    }
    public double laiSuat(double giaMua){
        if(giaMua <100000)
            return giaMua*0.08;
        else if(giaMua >=100000 && giaMua <500000)
            return giaMua*0.15;
        else
            return giaMua*0.2;
    }
    public double lamTronSo(double donGia){
        DecimalFormat format = new DecimalFormat("#,###");
        double giaSauKhiLamTron = Double.valueOf(format.format(donGia));
        return giaSauKhiLamTron;
    }
    public HoaDon timKiemHoaDonVoiMaHoaDon(String maHD){
        try {
            String sql = "select * from HoaDon where Ma_HoaDon = '"+maHD+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                NhanVien nv = nvDao.timNhanVien(rs.getString(4));
                KhachHang kh = khDAO.timKiem1KhachHangTheoMaKH(rs.getString(3));
                HoaDon hd = new HoaDon(rs.getString(1), rs.getDate(2), kh, nv);
                return hd;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean themHoaDon(HoaDon hd){
        if(timKiemHoaDonVoiMaHoaDon(hd.getMaHoaDon()) != null)
            return false;
        try {
            String sql = "INSERT INTO [dbo].[HoaDon]\n" +
                    "           ([Ma_HoaDon]\n" +
                    "           ,[NgayLap]\n" +
                    "           ,[Ma_KH]\n" +
                    "           ,[Ma_NV])\n" +
                    "     VALUES\n" +
                    "           (? ,? ,? ,? )";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hd.getMaHoaDon());
            stmt.setDate(2, (Date) hd.getNgayLap());
            stmt.setString(3, hd.getKhachHang().getMaKH());
            stmt.setString(4, hd.getNhanVien().getMaNV());
            int n = stmt.executeUpdate();
            return n> 0;
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public ArrayList<HoaDon> danhSachHoaDon(){
        try {
            ArrayList<HoaDon> list = new ArrayList<>();
            String sql = "select * from HoaDon";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhanVien nv = nvDao.timNhanVien(rs.getString(4));
                KhachHang kh = khDAO.timKiem1KhachHangTheoMaKH(rs.getString(3));
                HoaDon hd = new HoaDon(rs.getString(1), rs.getDate(2), kh, nv);
                list.add(hd);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public double  tongGiaTriKhiBietMaHD(String maHD){
        
        try {
            
            String sql = "select sum(SoLuong*DonGia) as price  from CTHoaDon where Ma_HoaDon = '"+maHD+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                double totalPrice = rs.getDouble(1);
                return totalPrice;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public ArrayList<HoaDon> dsHoaDonCoDieuKienNgay(java.util.Date fromDate, java.util.Date toDate){
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String startD = dateFormat.format(fromDate);
        String endD = dateFormat.format(toDate);
        
        String sql = "select * from HoaDon where NgayLap between  ? and ?";
        try {
            ArrayList<HoaDon> list = new ArrayList<>();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, startD);
            stmt.setString(2, endD);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                NhanVien nv = nvDao.timNhanVien(rs.getString(4));
                KhachHang kh = khDAO.timKiem1KhachHangTheoMaKH(rs.getString(3));
                HoaDon hd = new HoaDon(rs.getString(1), rs.getDate(2), kh, nv);
                list.add(hd);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
}
