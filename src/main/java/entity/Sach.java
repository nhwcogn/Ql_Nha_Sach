package entity;

public class Sach {
	private SanPham sanPham;
	private int soTrang;
	private int namSanXuat;
	private TacGia tacGia;
	private NhaXuatBan nhaXuatBan;

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setMaSach(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoTrang() {
		return soTrang;
	}

	public void setSoTrang(int soTrang) {
		this.soTrang = soTrang;
	}

	public int getNamSanXuat() {
		return namSanXuat;
	}

	public void setNamSanXuat(int namSanXuat) {
		this.namSanXuat = namSanXuat;
	}

	public TacGia getTacGia() {
		return tacGia;
	}

	public void setTacGia(TacGia tacGia) {
		this.tacGia = tacGia;
	}

	public NhaXuatBan getNhaXuatBan() {
		return nhaXuatBan;
	}

	public void setNhaXuatBan(NhaXuatBan nhaXuatBan) {
		this.nhaXuatBan = nhaXuatBan;
	}

	public Sach() {
		super();
	}

	public Sach(SanPham sanPham, int soTrang, int namSanXuat, TacGia tacGia, NhaXuatBan nhaXuatBan) {
		super();
		this.sanPham = sanPham;
		this.soTrang = soTrang;
		this.namSanXuat = namSanXuat;
		this.tacGia = tacGia;
		this.nhaXuatBan = nhaXuatBan;
	}

	@Override
	public String toString() {
		return "Sach [sanPham=" + sanPham + ", soTrang=" + soTrang + ", namSanXuat=" + namSanXuat + ", tacGia=" + tacGia
				+ ", nhaXuatBan=" + nhaXuatBan + "]";
	}

}
