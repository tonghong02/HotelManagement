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
public class ChiTietDatPhong {

	private String maDatPhong;
	private String maPhong;
	
	public ChiTietDatPhong(String record){
		String [] s = new String[2];
		s = record.split("\t",2);
		
		maDatPhong = s[0];
		maPhong = s[1];
	}

	public String getMaDatPhong() {
		return maDatPhong;
	}

	public void setMaDatPhong(String maDatPhong) {
		this.maDatPhong = maDatPhong;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String[] getValues(){
		String [] values = new String[2];
		
		values[0] = maDatPhong;
		values[1] = maPhong;
		
		return values;
	}
}
