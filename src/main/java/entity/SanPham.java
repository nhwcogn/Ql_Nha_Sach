package entity;

public class SanPham {
	private String ma_SanPham;
	private String tenSanPham;
	private double donGia;
	private int soLuong;
	private String dvTinh;
	private LoaiSanPham loaiSanPham;

	public String getMa_SanPham() {
		return ma_SanPham;
	}

	public void setMa_SanPham(String ma_SanPham) {
		this.ma_SanPham = ma_SanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public String getDvTinh() {
		return dvTinh;
	}

	public void setDvTinh(String dvTinh) {
		this.dvTinh = dvTinh;
	}

	public LoaiSanPham getLoaiSanPham() {
		return loaiSanPham;
	}

	public void setLoaiSanPham(LoaiSanPham loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}

	public SanPham() {
		super();
	}

	public SanPham(String ma_SanPham, String tenSanPham, double donGia, int soLuong, String dvTinh,
			LoaiSanPham loaiSanPham) {
		super();
		this.ma_SanPham = ma_SanPham;
		this.tenSanPham = tenSanPham;
		this.donGia = donGia;
		this.soLuong = soLuong;
		this.dvTinh = dvTinh;
		this.loaiSanPham = loaiSanPham;
	}

	@Override
	public String toString() {
		return "SanPham [ma_SanPham=" + ma_SanPham + ", tenSanPham=" + tenSanPham + ", donGia=" + donGia + ", soLuong="
				+ soLuong + ", dvTinh=" + dvTinh + ", loaiSanPham=" + loaiSanPham + "]";
	}

}
