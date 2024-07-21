package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBConnection;
import entity.NhaCungCap;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NhaCungCapDAO {
	private static Connection connection;
	public NhaCungCapDAO() {
		connection = DBConnection.getInstance().getConnection();
	}
	// tim kiem
	public NhaCungCap timKiemNhaCC(String maNCC)  {
            try {
                String sql = "select * from NhaCungCap where Ma_NhaCungCap = ? ";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1 , maNCC);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()) {
                    NhaCungCap ncc = new NhaCungCap(
                            rs.getString("Ma_NhaCungCap"),
                            rs.getString("[TenNCC]"),
                            rs.getString("[DiaChi]"),
                            rs.getString("[Email]"),
                            rs.getString("[SDT]"));
                    return ncc;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            return null;
	}
	//them
	public boolean themNhaCungCap(NhaCungCap ncc) throws Exception {
		if(timKiemNhaCC(ncc.getMaNhaCungCap()) != null)
			return false;
		String sql  = "INSERT INTO [dbo].[NhaCungCap]\r\n"
				+ "           ([Ma_NhaCungCap]\r\n"
				+ "           ,[TenNCC]\r\n"
				+ "           ,[DiaChi]\r\n"
				+ "           ,[Email]\r\n"
				+ "           ,[SDT])\r\n"
				+ "     VALUES\r\n"
				+ "           (?, ?, ?, ?, ? )";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, ncc.getMaNhaCungCap());
		stmt.setString(2, ncc.getTenNCC());
		stmt.setString(2, ncc.getDiaChi());
		stmt.setString(2, ncc.getEmail());
		stmt.setString(2, ncc.getsDT());
		
		int n = stmt.executeUpdate();
		return n>0;
	}
	//cap nhat
	public boolean capNhatNhaCC(NhaCungCap ncc) throws Exception{
		if(timKiemNhaCC(ncc.getMaNhaCungCap()) == null) 
			return false;
		String sql = "UPDATE [dbo].[NhaCungCap]\r\n"
				+ "   SET [TenNCC] = ? "
				+ "      ,[DiaChi] = ? "
				+ "      ,[Email] = ? "
				+ "      ,[SDT] = ? "
				+ " WHERE Ma_NhaCungCap = ? ";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, ncc.getTenNCC());
		stmt.setString(2, ncc.getDiaChi());
		stmt.setString(3, ncc.getEmail());
		stmt.setString(4, ncc.getsDT());
		stmt.setString(5, ncc.getMaNhaCungCap());
		
		int n = stmt.executeUpdate();
		return n> 0;
		
	}
	
	//xoa 
	public boolean xoaNhaCungCap(String maNCC)  {
            try {
                String sql = "DELETE FROM [dbo].[NhaCungCap]\r\n"
                        + "      WHERE Ma_NhaCungCap = ? ";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, maNCC);
                int n = stmt.executeUpdate();
                return n>0;
            } catch (SQLException ex) {
                Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
	}
	// get danh sach
	public ArrayList<NhaCungCap> dsNhaCungCap(){
            ArrayList<NhaCungCap> ds = new ArrayList<>();
            try {
                String sql = "select * from NhaCungCap";
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    NhaCungCap ncc = new NhaCungCap(rs.getString("Ma_NhaCungCap"),
                            rs.getString("TenNCC"),
                            rs.getString("DiaChi"),
                            rs.getString("Email"),
                            rs.getString("SDT"));
                    ds.add(ncc);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
           return ds; 
	}
        public ArrayList<NhaCungCap> timKiemDSNhaCCTheoMa(String maNCC){
            ArrayList<NhaCungCap> ds = new ArrayList<>();
            try {
                String sql = "select * from NhaCungCap where Ma_NhaCungCap like N'%"+maNCC+"%'";
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    NhaCungCap ncc = new NhaCungCap(rs.getString("Ma_NhaCungCap"),
                            rs.getString("TenNCC"),
                            rs.getString("DiaChi"),
                            rs.getString("Email"),
                            rs.getString("SDT"));
                    ds.add(ncc);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
           return ds; 
	}
        public String getMaCuoi(){
        String maNCC = "";
        String sql = "select * from KhachHang order by Ma_NhaCungCap desc";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                maNCC = rs.getString(1);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maNCC;
    }

}
