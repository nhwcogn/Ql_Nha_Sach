package entity;

import java.util.Date;

public class CT_Ca {
	private CaLamViec caLamViec;
	private NhanVien nhaVien;
	private Date ngayLam;

	public CaLamViec getCaLamViec() {
		return caLamViec;
	}

	public void setCaLamViec(CaLamViec caLamViec) {
		this.caLamViec = caLamViec;
	}

	public NhanVien getNhaVien() {
		return nhaVien;
	}

	public void setNhaVien(NhanVien nhaVien) {
		this.nhaVien = nhaVien;
	}

	public Date getNgayLam() {
		return ngayLam;
	}

	public void setNgayLam(Date ngayLam) {
		this.ngayLam = ngayLam;
	}

	public CT_Ca() {
		super();
	}

	public CT_Ca(CaLamViec caLamViec, NhanVien nhaVien, Date ngayLam) {
		super();
		this.caLamViec = caLamViec;
		this.nhaVien = nhaVien;
		this.ngayLam = ngayLam;
	}

	@Override
	public String toString() {
		return "CT_Ca [caLamViec=" + caLamViec + ", nhaVien=" + nhaVien + ", ngayLam=" + ngayLam + "]";
	}

}
