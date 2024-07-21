package entity;

public class CaLamViec {
	private String maCa;
	private String tenCa;

	public String getMaCa() {
		return maCa;
	}

	public void setMaCa(String maCa) {
		this.maCa = maCa;
	}

	public String getTenCa() {
		return tenCa;
	}

	public void setTenCa(String tenCa) {
		this.tenCa = tenCa;
	}

	public CaLamViec(String maCa, String tenCa) {
		super();
		this.maCa = maCa;
		this.tenCa = tenCa;
	}

        public CaLamViec(String maCa) {
		super();
		this.maCa = maCa;
	}
	public CaLamViec() {
		super();
	}

	@Override
	public String toString() {
		return "CaLamViec [maCa=" + maCa + ", tenCa=" + tenCa + "]";
	}

}
