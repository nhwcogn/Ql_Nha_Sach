
package dao;

import entity.NhaXuatBan;
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
public class NhaXuatBanDAO {
    private static Connection con;
    public NhaXuatBanDAO() {
        con = db.DBConnection.getInstance().getConnection();
    }
    
    public NhaXuatBan timKiemNXB(String maNXB){
        try {
            PreparedStatement stmt = con.prepareStatement("select * from NhaXuatBan  where Ma_NhaXuatBan = '"+maNXB+"'");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                NhaXuatBan nxb = new NhaXuatBan(rs.getString(1), rs.getString(2), rs.getString(3));
                return nxb;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public ArrayList<NhaXuatBan> danhSachNXB(){
        ArrayList<NhaXuatBan> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from NhaXuatBan");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhaXuatBan nxb = new NhaXuatBan(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(nxb);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public  NhaXuatBan timNXBTheoTen(String tenNXB){
        try {
            String sql = "select * from NhaXuatBan where TenNXB = N'"+tenNXB+"'";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                NhaXuatBan nxb = new NhaXuatBan(rs.getString(1), rs.getString(2), rs.getString(3));
                return nxb;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public ArrayList<NhaXuatBan> timKiemDanhSachNXBTheoMa(String maNXB){
        ArrayList<NhaXuatBan> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from NhaXuatBan  where Ma_NhaXuatBan like N'%"+maNXB+"%'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhaXuatBan nxb = new NhaXuatBan(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(nxb);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public ArrayList<NhaXuatBan> timKiemDanhSachNXBTheoTen(String tenNXB){
        ArrayList<NhaXuatBan> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("select * from NhaXuatBan  where TenNXB like N'%"+tenNXB+"%'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NhaXuatBan nxb = new NhaXuatBan(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(nxb);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean xoaNhaXuatBan( String maNXB){
        if(timKiemNXB(maNXB) == null)
            return false;
        String sql = "DELETE FROM [dbo].[NhaXuatBan]\n" +
"      WHERE Ma_NhaXuatBan = ? ";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNXB);
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public boolean themNhaXuatBan(NhaXuatBan nxb){
        if(timKiemNXB(nxb.getMaNhaXuatBan()) != null)
            return false;
        String sql = "INSERT INTO [dbo].[NhaXuatBan]\n" +
"           ([Ma_NhaXuatBan]\n" +
"           ,[TenNXB]\n" +
"           ,[DiaChi])\n" +
"     VALUES " +
"           ( ? ,? , ? )";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nxb.getMaNhaXuatBan());
            stmt.setString(2, nxb.getTenNXB());
            stmt.setString(3, nxb.getDiaChi());
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public boolean suaNhaXuatBan(NhaXuatBan nxb){
        if(timKiemNXB(nxb.getMaNhaXuatBan()) == null)
            return false;
        String sql = "UPDATE [dbo].[NhaXuatBan]\n" +
                    "   SET [TenNXB] = ? " +
                "      ,[DiaChi] = ? " +
                    " WHERE Ma_NhaXuatBan = ? ";
        PreparedStatement stmt;
        try {
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, nxb.getTenNXB());
            stmt.setString(2, nxb.getDiaChi());
            stmt.setString(3, nxb.getMaNhaXuatBan());
            int n = stmt.executeUpdate();
            return n>0;
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    public String getMaCuoi(){
        String maNXB = "";
        String sql = "select * from NhaXuatBan order by Ma_NhaXuatBan desc";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                maNXB = rs.getString(1);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maNXB;
    }
    
    public ArrayList<String> dsTenNXB(){
        ArrayList<String> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("select TenNXB from NhaXuatBan ");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String nxb = rs.getString(1);
                //NhaXuatBan nxb = new NhaXuatBan(rs.getString(1), rs.getString(2), rs.getString(3));
                list.add(nxb);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhaXuatBanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
}
