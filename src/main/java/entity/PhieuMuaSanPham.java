package entity;

import java.util.Date;

public class PhieuMuaSanPham {
	private String maPhieuNhapSP;
	private Date ngayNhap;
	private NhaCungCap nhaCungCap;
	private NhanVien nhanVien;

	public String getMaPhieuNhapSP() {
		return maPhieuNhapSP;
	}

	public void setMaPhieuNhapSP(String maPhieuNhapSP) {
		this.maPhieuNhapSP = maPhieuNhapSP;
	}

	public Date getNgayNhap() {
		return ngayNhap;
	}

	public void setNgayNhap(Date ngayNhap) {
		this.ngayNhap = ngayNhap;
	}

	public NhaCungCap getNhaCungCap() {
		return nhaCungCap;
	}

	public void setNhaCungCap(NhaCungCap nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public PhieuMuaSanPham(String maPhieuNhapSP, Date ngayNhap, NhaCungCap nhaCungCap, NhanVien nhanVien) {
		super();
		this.maPhieuNhapSP = maPhieuNhapSP;
		this.ngayNhap = ngayNhap;
		this.nhaCungCap = nhaCungCap;
		this.nhanVien = nhanVien;
	}

	public PhieuMuaSanPham() {
		super();
	}

	@Override
	public String toString() {
		return "PhieuMuaSanPham [maPhieuNhapSP=" + maPhieuNhapSP + ", ngayNhap=" + ngayNhap + ", nhaCungCap="
				+ nhaCungCap + ", nhanVien=" + nhanVien + "]";
	}

}
