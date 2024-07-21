package entity;

public class LoaiSanPham {
	private String maLoaiSP;
	private String loaiSP;
	private String tenLoai;

	public String getMaLoaiSP() {
		return maLoaiSP;
	}

	public void setMaLoaiSP(String maLoaiSP) {
		this.maLoaiSP = maLoaiSP;
	}

	public String getLoaiSP() {
		return loaiSP;
	}

	public void setLoaiSP(String loaiSP) {
		this.loaiSP = loaiSP;
	}

	public String getTenLoai() {
		return tenLoai;
	}

	public void setTenLoai(String tenLoai) {
		this.tenLoai = tenLoai;
	}

	public LoaiSanPham() {
		super();
	}

	public LoaiSanPham(String maLoaiSP, String loaiSP, String tenLoai) {
		super();
		this.maLoaiSP = maLoaiSP;
		this.loaiSP = loaiSP;
		this.tenLoai = tenLoai;
	}

	@Override
	public String toString() {
		return "LoaiSanPham [maLoaiSP=" + maLoaiSP + ", loaiSP=" + loaiSP + ", tenLoai=" + tenLoai + "]";
	}

}
