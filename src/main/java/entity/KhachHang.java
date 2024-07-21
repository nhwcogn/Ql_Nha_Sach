package entity;

public class KhachHang {
	private String maKH;
	private String tenKH;
	private String sDT;
	private String diaChi;
	private String email;

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getsDT() {
		return sDT;
	}

	public void setsDT(String sDT) {
		this.sDT = sDT;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public KhachHang() {
		super();
	}

	public KhachHang(String maKH, String tenKH, String sDT, String diaChi, String email) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.sDT = sDT;
		this.diaChi = diaChi;
		this.email = email;
	}

	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", sDT=" + sDT + ", diaChi=" + diaChi + ", email="
				+ email + "]";
	}

}
