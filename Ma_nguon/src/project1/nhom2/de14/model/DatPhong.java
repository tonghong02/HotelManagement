/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.model;

import java.util.GregorianCalendar;
import project1.nhom2.de14.util.CalendarAdapter;

/**
 *
 * @author LeThanhLoi
 */
public class DatPhong {

	private String maDatPhong;
	private String maKH;
	private GregorianCalendar thoiGianNhan;
	private GregorianCalendar thoiGianTra;
	private int soPhongDat;
	private double tienDatCoc;
	private String maNV;
	
	public DatPhong(String record){
		String [] s = new String[7];
		s = record.split("\t",7);
		
		maDatPhong = s[0];
		maKH = s[1];
		if(!s[2].equals("null") && !s[2].equals("") ){
	        	thoiGianNhan = new GregorianCalendar();
		        thoiGianNhan.setTime(CalendarAdapter.StringToDate( s[2]));
		}
		if(!s[3].equals("null") &&  !s[3].equals("") ){
			thoiGianTra = new GregorianCalendar();
		        thoiGianTra.setTime(CalendarAdapter.StringToDate( s[3]));
		}
		soPhongDat = Integer.parseInt(s[4]);
		tienDatCoc = Double.parseDouble(s[5]);
		maNV = s[6];
	}
	
	public String getMaDatPhong() {
		return maDatPhong;
	}

	public void setMaDatPhong(String maDatPhong) {
		this.maDatPhong = maDatPhong;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public GregorianCalendar getThoiGianNhan() {
		return thoiGianNhan;
	}
	public String getThoiGianNhanString() {
		return CalendarAdapter.formatCalendar(thoiGianNhan);
	}
	
	public void setThoiGianNhan(GregorianCalendar thoiGianNhan) {
		this.thoiGianNhan = thoiGianNhan;
	}

	public GregorianCalendar getThoiGianTra() {
		return thoiGianTra;
	}
	public String getThoiGianTraString() {
		return CalendarAdapter.formatCalendar(thoiGianTra);
	}

	public void setThoiGianTra(GregorianCalendar thoiGianTra) {
		this.thoiGianTra = thoiGianTra;
	}

	public int getSoPhongDat() {
		return soPhongDat;
	}

	public void setSoPhongDat(int soPhongDat) {
		this.soPhongDat = soPhongDat;
	}

	public double getTienDatCoc() {
		return tienDatCoc;
	}

	public void setTienDatCoc(double tienDatCoc) {
		this.tienDatCoc = tienDatCoc;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String[] getValues(){
		String [] values = new String[7];
		
		values[0] = maDatPhong;
		values[1] = maKH;
		values[2] = getThoiGianNhanString();
		values[3] = getThoiGianTraString();
		values[4] = Integer.toString(soPhongDat);
		values[5] = Double.toString(tienDatCoc);
		values[6] = maNV;
		
		return values;
	}
}
