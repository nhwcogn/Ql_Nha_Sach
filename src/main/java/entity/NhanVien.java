package entity;

import java.util.Date;

public class NhanVien {
	private String maNV;
	private String tenNV;
	private Date ngaySinh;
	private String cCCD;
	private String diaChi;
	private String sDT;
	private String chucVu;
	private NhanVien nvQuanLy;

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getcCCD() {
		return cCCD;
	}

	public void setcCCD(String cCCD) {
		this.cCCD = cCCD;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getsDT() {
		return sDT;
	}

	public void setsDT(String sDT) {
		this.sDT = sDT;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public NhanVien getNvQuanLy() {
		return nvQuanLy;
	}

	public void setNvQuanLy(NhanVien nvQuanLy) {
		this.nvQuanLy = nvQuanLy;
	}

	public NhanVien() {
		super();
	}
	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}


	public NhanVien(String maNV, String tenNV, Date ngaySinh, String cCCD, String diaChi, String sDT, String chucVu,
			NhanVien nvQuanLy) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		this.cCCD = cCCD;
		this.diaChi = diaChi;
		this.sDT = sDT;
		this.chucVu = chucVu;
		this.nvQuanLy = nvQuanLy;
	}

	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", ngaySinh=" + ngaySinh + ", cCCD=" + cCCD + ", diaChi="
				+ diaChi + ", sDT=" + sDT + ", chucVu=" + chucVu + ", nvQuanLy=" + nvQuanLy + "]";
	}

}
