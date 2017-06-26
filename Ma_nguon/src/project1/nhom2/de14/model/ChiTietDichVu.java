package project1.nhom2.de14.model;

public class ChiTietDichVu {

	private String maPhong;
	private String maDV;
	private int soLuong;
	private double thanhTien;
	
	public ChiTietDichVu(String record){
		String [] s = new String[4];
		s = record.split("\t", 4);
		
		maPhong = s[0];
		maDV = s[1];
		soLuong = Integer.parseInt(s[2]);
		thanhTien = Double.parseDouble(s[3]);
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String getMaDV() {
		return maDV;
	}

	public void setMaDV(String maDV) {
		this.maDV = maDV;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}

	public String[] getValues() {
		String [] values = new String[4];
		
		values[0] = maPhong;
		values[1] = maDV;
		values[2] = Integer.toString(soLuong);
		values[3] = Double.toString(thanhTien); 
		
		return values;
	}
}
