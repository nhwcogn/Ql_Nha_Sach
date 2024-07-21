package dao;

import db.DBConnection;
import entity.KhachHang;
import entity.NhanVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author letha
 */
public class NhanVienDAO {
    private Connection connection;
/**
 * contructor 
 */
    public NhanVienDAO() {
        connection =  DBConnection.getInstance().getConnection();
    }
    
    public NhanVien timNhanVien(String maNV)  {
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where Ma_NhanVien like ?");
            stmt.setString(1, "%" + maNV + "%");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
                        rs.getString("TenNV"),
                        rs.getDate("NamSinh"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        new NhanVien(rs.getString("ID_QuanLy"))
                );
                return nv;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }
    
    public NhanVien tim1NhanVienVoiTenNV(String tenNV)  {
        try {
            //ArrayList<NhanVien> listNV = new ArrayList<>();
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where TenNV like ?");
            stmt.setString(1, "%" + tenNV + "%");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
                        rs.getString("TenNV"),
                        rs.getDate("NamSinh"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        new NhanVien(rs.getString("ID_QuanLy"))
                );
                return nv;
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;
    }
    /**
     * tim kiem nhan vien khi biet ma nhan vien
     * @param maNV
     * @return
     * @throws Exception
     */
    public ArrayList<NhanVien> timNhanVienVoiMaNV(String maNV) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where Ma_NhanVien like ?");
            stmt.setString(1, "%" + maNV + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
                        rs.getString("TenNV"),
                        rs.getDate("NamSinh"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        new NhanVien(rs.getString("ID_QuanLy"))
                );
                listNV.add(nv);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNV;
    }
    
    public ArrayList<String> getDSTenNV(String tenNV) {
        ArrayList<String> listNV = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where TenNV like ?");
            stmt.setString(1, "%" + tenNV + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String nv = rs.getString("TenNV");
                listNV.add(nv);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNV;
    }
    
    public ArrayList<NhanVien> timNhanVienVoiSDT(String sDT) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where SDT like ?");
            stmt.setString(1, "%" + sDT + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
                        rs.getString("TenNV"),
                        rs.getDate("NamSinh"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        new NhanVien(rs.getString("ID_QuanLy"))
                );
                listNV.add(nv);
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNV;
    }
    public ArrayList<NhanVien> timNhanVienVoiTenNV(String ten) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where TenNV like ?");
            stmt.setString(1, "%" + ten + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
                        rs.getString("TenNV"),
                        rs.getDate("NamSinh"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        new NhanVien(rs.getString("ID_QuanLy"))
                );
                listNV.add(nv);
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNV;
    }
    public ArrayList<NhanVien> timNhanVienVoiCCCD(String cCCD) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where CCCD like ?");
            stmt.setString(1, "%" + cCCD + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
                        rs.getString("TenNV"),
                        rs.getDate("NamSinh"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        new NhanVien(rs.getString("ID_QuanLy"))
                );
                listNV.add(nv);
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNV;
    }
    
    public ArrayList<NhanVien> timNhanVienVoiMaVaTen(String ma, String ten) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from NhanVien where Ma_NhanVien = ? AND TenNV = ?");
            stmt.setString(1, ma);
            stmt.setString(1, ten);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
                        rs.getString("TenNV"),
                        rs.getDate("NamSinh"),
                        rs.getString("CCCD"),
                        rs.getString("DiaChi"),
                        rs.getString("SDT"),
                        rs.getString("ChucVu"),
                        new NhanVien(rs.getString("ID_QuanLy"))
                );
                listNV.add(nv);
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNV;
    }
    /**
     * them 1 nhan vien 
     * @param nv
     * @return true neu them thanh cong false neu that bai
     * @throws Exception
     */
    public boolean themNhanVien(NhanVien nv) throws Exception {
    	if(timNhanVien(nv.getMaNV()) != null)
    		return false;
    	String sql = "INSERT INTO [dbo].[NhanVien]\r\n"
    			+ "           ([Ma_NhanVien]\r\n"
    			+ "           ,[TenNV]\r\n"
    			+ "           ,[NamSinh]\r\n"
    			+ "           ,[CCCD]\r\n"
    			+ "           ,[DiaChi]\r\n"
    			+ "           ,[SDT]\r\n"
    			+ "           ,[ChucVu]\r\n"
    			+ "           ,[ID_QuanLy])\r\n"
    			+ "     VALUES\r\n"
    			+ "           (?, ?, ?, ?, ?, ?, ? ,?)";
    	PreparedStatement stmt = connection.prepareStatement(sql);
    	stmt.setString(1, nv.getMaNV());
    	stmt.setString(2, nv.getTenNV());
    	stmt.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
    	stmt.setString(4, nv.getcCCD());
    	stmt.setString(5, nv.getDiaChi());
    	stmt.setString(6, nv.getsDT());
    	stmt.setString(7, nv.getChucVu());
    	stmt.setString(8, nv.getNvQuanLy().getMaNV());
    	int n = stmt.executeUpdate();
    	return n>0;
    }
    /**
     * cap nhat nhan vien 
     * @param nv
     * @return true neu thanh cong va false neu that bai
     * @throws Exception
     */
    
    public boolean capNhatNhanVien(NhanVien nv) throws Exception {
    	if(timNhanVienVoiMaNV(nv.getMaNV()) == null)
    		return false;
    	String sql = "UPDATE [dbo].[NhanVien]\r\n"
    			+ "   SET [TenNV] = ? "
    			+ "      ,[NamSinh] = ? "
    			+ "      ,[CCCD] = ? "
    			+ "      ,[DiaChi] = ? "
    			+ "      ,[SDT] = ? "
    			+ "      ,[ChucVu] = ? "
    			+ "      ,[ID_QuanLy] = ? "
    			+ " WHERE Ma_NhanVien = ?";
    	PreparedStatement stmt = connection.prepareStatement(sql);
    	stmt.setString(1, nv.getTenNV());
    	stmt.setDate(2, new java.sql.Date(nv.getNgaySinh().getTime()));
    	stmt.setString(3, nv.getcCCD());
    	stmt.setString(4, nv.getDiaChi());
    	stmt.setString(5, nv.getsDT());
    	stmt.setString(6, nv.getChucVu());
    	stmt.setString(7, nv.getNvQuanLy().getMaNV());
    	stmt.setString(8, nv.getMaNV());
    	int n = stmt.executeUpdate();
    	return n>0;
    }
    
    /**
     * ham thuc hien xoa nhan vien khi biet ma nhan vien
     * @param maNV
     * @return true neu thanh cong va false neu that bai
     * @throws Exception
     */
    public boolean xoaNhanVien(String maNV) throws Exception{
    	if(timNhanVienVoiMaNV(maNV) == null)
    		return false;
    	String sql = "DELETE FROM [dbo].[NhanVien]\r\n"
    			+ "      WHERE Ma_NhanVien  = ? ";
    	
    	PreparedStatement stmt = connection.prepareStatement(sql);
    	stmt.setString(1, maNV);
    	int n = stmt.executeUpdate();
    	return n>0;
    }
    
    /**
     * 
     * @return tra ve 1 danh sach nhan vien
     */
    public ArrayList<NhanVien> getListNhanVien(){
    	ArrayList<NhanVien> listNV = new ArrayList<>();
    	try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from NhanVien");
			while(rs.next()) {
				
				Date ngaySinh = rs.getDate("NamSinh");
				NhanVien nv = new NhanVien(rs.getString("Ma_NhanVien"),
						rs.getString("TenNV"),
						ngaySinh,
						rs.getString("CCCD"),
						rs.getString("DiaChi"),
						rs.getString("SDT"),
						rs.getString("ChucVu"),
						new NhanVien(rs.getString("ID_QuanLy"))
						);
				listNV.add(nv);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return listNV;
    }
    
    public String getMaNVCuoi() {
		String maNV = "";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("Select ma_NhanVien from NhanVien");
			while (rs.next()) {
				maNV = rs.getString("ma_NhanVien");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maNV;
	}
    
}
