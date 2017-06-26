package project1.nhom2.de14.model;

import java.util.GregorianCalendar;
import project1.nhom2.de14.util.CalendarAdapter;

public class HoaDon {

	private String maHD;
	private String maDatPhong;
	private GregorianCalendar thoiGianThanhToan;
	private double tienPhong;
	private double tienDichvu;
	
	public HoaDon(String record){
		String[] s = new String[5];
		s = record.split("\t",5);
		
		maHD = s[0];
		maDatPhong = s[1];
		thoiGianThanhToan = new GregorianCalendar();
		thoiGianThanhToan.setTime(CalendarAdapter.StringToDate(s[2]));
		tienPhong = Double.parseDouble(s[3]);
		tienDichvu = Double.parseDouble(s[4]);
	}

	public String getMaHD() {
		return maHD;
	}

	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}

	public String getMaDatHang() {
		return maDatPhong;
	}

	public void setMaDatHang(String maDatPhong) {
		this.maDatPhong = maDatPhong;
	}

	public GregorianCalendar getThoiGianThanhToan() {
		return thoiGianThanhToan;
	}
	public String getThoiGianThanhToanString(){
		return CalendarAdapter.formatCalendar(thoiGianThanhToan);
	}
	
	public void setThoiGianThanhToan(GregorianCalendar thoiGianThanhToan) {
		this.thoiGianThanhToan = thoiGianThanhToan;
	}

	public double getTienPhong() {
		return tienPhong;
	}

	public void setTienPhong(double tienPhong) {
		this.tienPhong = tienPhong;
	}

	public double getTienDichvu() {
		return tienDichvu;
	}

	public void setTienDichvu(double tienDichvu) {
		this.tienDichvu = tienDichvu;
	}

	public String[] getValues(){
		String [] values = new String[5];
		
		values[0] = maHD;
		values[1] = maDatPhong;
		values[2] = getThoiGianThanhToanString();
		values[3] = Double.toString(tienPhong);
		values[4] = Double.toString(tienDichvu);
		
		return values;
	}
}
