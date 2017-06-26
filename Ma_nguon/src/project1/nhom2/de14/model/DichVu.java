/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1.nhom2.de14.model;

/**
 *
 * @author LeThanhLoi
 */
public class DichVu {

	private String maDV;
	private String tenDV;
	private double donGia;
	private String maNVPhuTrach;

	public DichVu(String record){
		String [] s = new String[4];
		s = record.split("\t", 4);
		
		maDV = s[0];
		tenDV = s[1];
		donGia = Double.parseDouble(s[2]);
		maNVPhuTrach = s[3];
		System.out.println(maNVPhuTrach+"x");
	}
	
	public String getMaDV() {
		return maDV;
	}

	public void setMaDV(String maDV) {
		this.maDV = maDV;
	}

	public String getTenDV() {
		return tenDV;
	}

	public void setTenDV(String tenDV) {
		this.tenDV = tenDV;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public String getMaNVPhuTrach() {
		return maNVPhuTrach;
	}

	public void setMaNVPhuTrach(String maNVPhuTrach) {
		this.maNVPhuTrach = maNVPhuTrach;
	}
	
	public String[] getValues(){
		String [] values = new String[4];
		
		values[0] = maDV;
		values[1] = tenDV;
		values[2] = Double.toString(donGia);
		values[3] = maNVPhuTrach;
		
		return values;
	}
}
