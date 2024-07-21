package entity;

public class NhaCungCap {
	private String maNhaCungCap;
	private String tenNCC;
	private String diaChi;
	private String email;
	private String sDT;

	public String getMaNhaCungCap() {
		return maNhaCungCap;
	}

	public void setMaNhaCungCap(String maNhaCungCap) {
		this.maNhaCungCap = maNhaCungCap;
	}

	public String getTenNCC() {
		return tenNCC;
	}

	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
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

	public String getsDT() {
		return sDT;
	}

	public void setsDT(String sDT) {
		this.sDT = sDT;
	}

	public NhaCungCap(String maNhaCungCap, String tenNCC, String diaChi, String email, String sDT) {
		super();
		this.maNhaCungCap = maNhaCungCap;
		this.tenNCC = tenNCC;
		this.diaChi = diaChi;
		this.email = email;
		this.sDT = sDT;
	}

	public NhaCungCap() {
		super();
	}

	@Override
	public String toString() {
		return "NhaCungCap [maNhaCungCap=" + maNhaCungCap + ", tenNCC=" + tenNCC + ", diaChi=" + diaChi + ", email="
				+ email + ", sDT=" + sDT + "]";
	}

}
