package entity;

public class CT_PhieuMuaSanPham {
	private PhieuMuaSanPham phieuMuaSanPham;
	private SanPham sanPham;
	private int soLuong;
	private double donGia;

	public PhieuMuaSanPham getPhieuMuaSanPham() {
		return phieuMuaSanPham;
	}

	public void setPhieuMuaSanPham(PhieuMuaSanPham phieuMuaSanPham) {
		this.phieuMuaSanPham = phieuMuaSanPham;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public CT_PhieuMuaSanPham() {
		super();
	}

	public CT_PhieuMuaSanPham(PhieuMuaSanPham phieuMuaSanPham, SanPham sanPham, int soLuong, double donGia) {
		super();
		this.phieuMuaSanPham = phieuMuaSanPham;
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.donGia = donGia;
	}

	@Override
	public String toString() {
		return "CT_PhieuMuaSanPham [phieuMuaSanPham=" + phieuMuaSanPham + ", sanPham=" + sanPham + ", soLuong="
				+ soLuong + ", donGia=" + donGia + "]";
	}

}
