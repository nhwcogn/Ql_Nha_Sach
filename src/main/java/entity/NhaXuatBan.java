package entity;

public class NhaXuatBan {
	private String maNhaXuatBan;
	private String tenNXB;
	private String DiaChi;

	public String getMaNhaXuatBan() {
		return maNhaXuatBan;
	}

	public void setMaNhaXuatBan(String maNhaXuatBan) {
		this.maNhaXuatBan = maNhaXuatBan;
	}

	public String getTenNXB() {
		return tenNXB;
	}

	public void setTenNXB(String tenNXB) {
		this.tenNXB = tenNXB;
	}

	public String getDiaChi() {
		return DiaChi;
	}

	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}

	public NhaXuatBan() {
		super();
	}

	public NhaXuatBan(String maNhaXuatBan, String tenNXB, String diaChi) {
		super();
		this.maNhaXuatBan = maNhaXuatBan;
		this.tenNXB = tenNXB;
		DiaChi = diaChi;
	}
        public NhaXuatBan(String maNhaXuatBan) {
		super();
		this.maNhaXuatBan = maNhaXuatBan;
		
	}

	@Override
	public String toString() {
		return "NhaXuatBan [maNhaXuatBan=" + maNhaXuatBan + ", tenNXB=" + tenNXB + ", DiaChi=" + DiaChi + "]";
	}

}
