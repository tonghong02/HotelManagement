/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.NhanVien;
import project1.nhom2.de14.util.ConnectDB;
import project1.nhom2.de14.util.WriteRead;

/**
 *
 * @author WILL
 */
public class CNhanVien {
	
	private Vector<NhanVien> DS;
	private ConnectDB conn;
	
	public CNhanVien(String user, String pass) {
		DS = new Vector<NhanVien>(100);
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);
		
		String[] key = null;
		
		Vector<String> vec = conn.select("nhanvien", key);
		for (String s : vec) {
			DS.add(new NhanVien(s));
		}
	}
	
	public int searchIndex(String maNV) {
		int i = 0;
		for (NhanVien nv : DS) {
			if (nv.getMaNV().equals(maNV)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public boolean add(String record) {
		NhanVien nv = new NhanVien(record);
		String[] values = nv.getValues();
		
		if (!conn.insert("nhanvien", values)) {
			return false;
		}
		
		DS.add(nv);
		return true;
	}
	
	public boolean delete(String key) {
		if (!conn.delete("nhanvien", "MaNV", key)) {
			return false;
		}
		
		int pos = searchIndex(key);
		DS.remove(pos);
		return true;
	}
	
	public boolean edit(String record, String key) {
		NhanVien nv = new NhanVien(record);
		String[] values = nv.getValues();
		
		values[0] = "MaNV=" + "'" + values[0] + "'";
		values[1] = "TenNV=" + "'" + values[1] + "'";
		values[2] = "NgaySinh=" + "STR_TO_DATE(" + "'" + values[2] + "'," + "'%d-%m-%Y')";
		values[3] = "GioiTinh=" + "'" + values[3] + "'";
		values[4] = "SoCMND=" + "'" + values[4] + "'";
		values[5] = "DiaChi=" + "'" + values[5] + "'";
		values[6] = "ChucVu = " + "'" + values[6] + "'";
		values[7] = "SDT=" + "'" + values[7] + "'";
		
		String pkey = "MaNV=" + "'" + key + "'";
		if (!conn.update("nhanvien", values, pkey)) {
			return false;
		}
		
		int pos = searchIndex(key);
		DS.remove(pos);
		DS.insertElementAt(nv, pos);
		return true;
	}
	
	public Vector<String> addFile(String fileName) {
		Vector<String> vec = WriteRead.read(fileName);
		Vector<String> vec2 = new Vector<String>(10);
		
		for (String s : vec) {
			NhanVien nv = new NhanVien(s);
			String[] values = nv.getValues();
			if (!conn.insert("nhanvien", values)) {
				continue;
			}
			vec2.add(s);
			DS.add(nv);
		}
		
		return vec2;
	}
	
	public Vector<NhanVien> getDS() {
		return DS;
	}
	
	public Object[][] getTableData() {
		if (DS == null) {
			return new Object[0][8];
		}
		Object[][] data = new Object[DS.size()][8];
		
		int i = 0;
		for (NhanVien nv : DS) {
			data[i] = nv.getValues();
			i++;
		}
		
		return data;
	}
	
	public String[] getIds() {
		String[] ids = new String[DS.size()];
		for (int i = 0; i < DS.size(); i++) {
			ids[i] = DS.get(i).getMaNV();
		}
		return ids;
	}
}
