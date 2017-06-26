/*
 * Project 1-T6-14h45-20161
 * Nhom 2-De 14
 * Phan Ngoc Lan
 * Le Thanh Loi
 * Tong Thi Hong
 */
package project1.nhom2.de14.controller;

import java.util.Vector;
import project1.nhom2.de14.model.KhachHang;
import project1.nhom2.de14.util.*;

/**
 *
 * @author WILL
 */
public class CKhachHang {

	private Vector<KhachHang> DS;
	private ConnectDB conn;

	public CKhachHang(String user, String pass) {
		conn = new ConnectDB();
		conn.connect("khachsan", UserInfo.username, UserInfo.password);
		String[] key = null;
		DS = new Vector<KhachHang>(100);

		Vector<String> vec = conn.select("khachhang", key);
		for (String s : vec) {
			DS.add(new KhachHang(s));
		}
	}

	public int searchIndex(String maKH) {
		int i = 0;
		for (KhachHang p : DS) {
			if (p.getMaKH().equals(maKH)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public boolean add(String record) {
		KhachHang kh = new KhachHang(record);
		String[] values = new String[7];
		values = record.split("\t", 7);

		if (!conn.insert("khachhang", values)) {
			return false;
		}

		DS.add(kh);
		return true;
	}

	public boolean delete(String key) {
		if (!conn.delete("khachhang", "MaKH", key)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		return true;
	}

	public boolean edit(String record, String key) {
		String[] values = new String[7];
		values = record.split("\t", 7);
		values[0] = "MaKH=" + "'" + values[0] + "'";
		values[1] = "tenKH=" + "'" + values[1] + "'";
		values[2] = "soCMND=" + values[2];
		values[3] = "GioiTinh=" + "'" + values[3] + "'";
		values[4] = "DiaChi=" + "'" + values[4] + "'";
		values[5] = "QuocTich=" + "'" + values[5] + "'";
		values[6] = "SDT=" + "'" + values[6] + "'";
		String pkey = "MaKH=" + "'" + key + "'";
		if (!conn.update("khachhang", values, pkey)) {
			return false;
		}

		int pos = searchIndex(key);
		DS.remove(pos);
		DS.insertElementAt(new KhachHang(record), pos);
		return true;
	}

	public Vector<String> addFile(String fileName) {
		Vector<String> vec = WriteRead.read(fileName);
Vector<String> vec2 = new Vector<String>(10);

		for (String s : vec) {
			KhachHang p = new KhachHang(s);
			String[] values = p.getValues();
			if (!conn.insert("khachhang", values)) {
				continue;
			}
			DS.add(p);
			vec2.add(s);
		}

		return vec2;
	}

	public Vector<KhachHang> getDS() {
		return DS;
	}

	public Object[][] getTableData() {
		if (DS == null) {
			return new Object[0][7];
		}
		Object[][] data = new Object[DS.size()][7];

		int i = 0;
		for (KhachHang p : DS) {
			data[i] = p.getValues();
                        System.out.println(data[i][6]);
			i++;
		}

		return data;
	}

	public String[] getIds(){
		String[] ids = new String[DS.size()];
		for(int i=0;i<DS.size();i++){
			ids[i] = DS.get(i).getMaKH();
		}
		return ids;
	}
}
