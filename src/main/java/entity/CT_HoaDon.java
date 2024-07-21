package entity;

public class CT_HoaDon {
	private int soLuong;
	private double donGia;
	private HoaDon hoaDon;
	private SanPham sanPham;

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

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public CT_HoaDon() {
		super();
	}

	public CT_HoaDon(int soLuong, double donGia, HoaDon hoaDon, SanPham sanPham) {
		super();
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
	}

	@Override
	public String toString() {
		return "CT_HoaDon [soLuong=" + soLuong + ", donGia=" + donGia + ", hoaDon=" + hoaDon + ", sanPham=" + sanPham
				+ "]";
	}

}
