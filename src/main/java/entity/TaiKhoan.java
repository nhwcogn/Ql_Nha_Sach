package entity;

public class TaiKhoan {
	private String maNhanVien;
	private String matKhau;

	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public TaiKhoan() {
		super();
	}

	public TaiKhoan(String maNhanVien, String matKhau) {
		super();
		this.maNhanVien = maNhanVien;
		this.matKhau = matKhau;
	}

	@Override
	public String toString() {
		return "TaiKhoan [maNhanVien=" + maNhanVien + ", matKhau=" + matKhau + "]";
	}

}
