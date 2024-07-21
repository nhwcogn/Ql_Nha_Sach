package dao;

import db.DBConnection;
import entity.CT_HoaDon;
import entity.HoaDon;
import entity.SanPham;
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
public class CTHoaDonDAO {
    private Connection con;
    private SanPhamDAO spDao = new SanPhamDAO();
    private HoaDonDAO hdDao = new HoaDonDAO();

    public CTHoaDonDAO() {
        con = DBConnection.getInstance().getConnection();
    }
    
    public CT_HoaDon timKiem1CTHoaDon(String maHD, String maSP){
        
        try {
            String sql = "select * from CTHoaDon where Ma_SanPham = '"+maSP+"' and Ma_HoaDon = '"+maHD+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                SanPham sp = spDao.timKiemCoSach(maSP);
                HoaDon hd = hdDao.timKiemHoaDonVoiMaHoaDon(maHD);
                int soLuong = rs.getInt(3);
                double donGia = rs.getDouble(4);
                CT_HoaDon cthd = new CT_HoaDon(soLuong, donGia, hd, sp);
                return cthd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CTHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean themCT_HoaDon(CT_HoaDon cthd){
        if(timKiem1CTHoaDon(cthd.getHoaDon().getMaHoaDon(), cthd.getSanPham().getMa_SanPham()) != null)
            return false;
        try {
            String sql = "INSERT INTO [dbo].[CTHoaDon]\n" +
                    "           ([Ma_HoaDon]\n" +
                    "           ,[Ma_SanPham]\n" +
                    "           ,[SoLuong]\n" +
                    "           ,[DonGia])\n" +
                    "     VALUES\n" +
                    "           (? ,? ,? ,? )";
      
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cthd.getHoaDon().getMaHoaDon());
            stmt.setString(2, cthd.getSanPham().getMa_SanPham());
            stmt.setInt(3, cthd.getSoLuong());
            stmt.setDouble(4, cthd.getDonGia());
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(CTHoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    
    
    
}
